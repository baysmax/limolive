package com.example.project.limolive.tencentlive.views;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.activity.CommitOrdersActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.GoodsOrder2Been;
import com.example.project.limolive.bean.RechargeBeen;
import com.example.project.limolive.bean.order.CommitOrderBean;
import com.example.project.limolive.bean.order.CommitOrdersBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.pay.wx.WXPayUtils;
import com.example.project.limolive.utils.Constant;
import com.example.project.limolive.utils.ToastUtils;

/**
 * Created by hwj on 2016/6/3.
 * 支付页面
 */
public class PayReadyActivity extends BaseActivity implements View.OnClickListener{

    private String beizhu = "";
    private String cartbeizhu="";
    //1是商品支付,2是充值
    private String type = "";
    private String lemon_id = "";
    private CommitOrderBean cob;
    private CommitOrdersBean cobs;
    private RelativeLayout check_ali_pay,check_wx_pay;
    private TextView pay_check_box_ali,pay_check_box_wx;

    private boolean ali_boo=false;
    private boolean wx_boo=false;
    private TextView go_to_pay;
    private String ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ready);
        type = getIntent().getStringExtra("type");
        Log.i("type","type="+type);
        if ("1".equals(type)) {
            cob = (CommitOrderBean) getIntent().getSerializableExtra("cob");
            beizhu = getIntent().getStringExtra("beizhu");
        }if ("3".equals(type)){
            cobs= (CommitOrdersBean) getIntent().getSerializableExtra("cobs");
            ids = getIntent().getStringExtra("ids");
            //备注
        }else if ("2".equals(type)) {
            lemon_id = getIntent().getStringExtra("lemon_id");
        }
        setUpView();
    }

    private void setUpView() {
        pay_check_box_ali= (TextView) findViewById(R.id.pay_check_box_ali);
        pay_check_box_wx= (TextView) findViewById(R.id.pay_check_box_wx);
        selectAli();
        check_ali_pay= (RelativeLayout) findViewById(R.id.check_ali_pay);
        check_wx_pay= (RelativeLayout) findViewById(R.id.check_wx_pay);
        check_ali_pay.setOnClickListener(this);
        check_wx_pay.setOnClickListener(this);

        go_to_pay= (TextView) findViewById(R.id.go_to_pay);
        go_to_pay.setOnClickListener(this);
    }
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 0:
//                    String order_sn= (String) msg.obj;
//                    PayReadyActivity.this.order_sn.append(order_sn);
//                    WXPayUtils wxPayUtils = new WXPayUtils(PayReadyActivity.this, Constant.WXNOTIFY_URL);
//                    Log.i("main", "order_sn="+PayReadyActivity.this.order_sn.toString()+",cobs.getTotalPrice()="+cobs.getTotalPrice()+",order_sn.toString()="+order_sn.toString());
//                    wxPayUtils.pay("购物", cobs.getTotalPrice(), PayReadyActivity.this.order_sn.toString());
//                    break;
//                case 1:
//                    String s= (String) msg.obj;
//                    PayReadyActivity.this.order_sn.append(s)/*.append(",")*/;
//                    break;
//            }
//        }
//    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.title_back_button:
                finish();
                break;
            case R.id.check_ali_pay:
                selectAli();
                break;
            case R.id.check_wx_pay:
                selectWx();
                break;
            case R.id.go_to_pay:
                jumpToPay();
                break;
        }
    }

    /**
     * 跳到支付页面
     */
    private void jumpToPay() {
            if(ali_boo){
//                ToastUtils.showShort(this,"支付宝准备支付"+money+"元");
            }else if(wx_boo){
                if ("1".equals(type)) {
                    if (null != cob) {
                        String addressId = cob.getAddressList().getAddress_id();
                        String goosId = cob.getCartList().getGoods_id();
                        String goosNum = cob.getCartList().getGoods_num();
                        goos_Order2(LoginManager.getInstance().getUserID(this), goosId, addressId, goosNum, beizhu);
                    }
                } else if ("2".equals(type)) {
                    Recharge(LoginManager.getInstance().getUserID(this), lemon_id);
                }else if ("3".equals(type)){
                    ToastUtils.showShort(this,"type="+type);
                        String address_id = cobs.getAddressList().getAddress_id();
                        Cart3(LoginManager.getInstance().getUserID(this), address_id, beizhu,ids);

                }
            }
    }

    /**
     * 购物车支付
     * @param userID
     * @param address_id
     * @param remark
     * @param ids
     */
    private void Cart3(String userID, String address_id, String remark, String ids) {

        Api.Cart3(userID,address_id, remark,ids, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    GoodsOrder2Been gob = JSON.parseObject(apiResponse.getData(), GoodsOrder2Been.class);
                    WXPayUtils wxPayUtils = new WXPayUtils(PayReadyActivity.this, Constant.WXNOTIFY_URL);
                    wxPayUtils.pay("购物", cobs.getTotalPrice(), gob.getOrder_sn());
                }
            }
        });

    }

    /**
     * 选择微信支付
     */
    private void selectWx() {
        selectPayType(false,true);
    }

    /**
     * 选择支付宝
     */
    private void selectAli() {
        selectPayType(true,false);
    }

    private void selectPayType(boolean ali,boolean wx){
        this.ali_boo=ali;
        this.wx_boo=wx;
        if(ali){
            pay_check_box_ali.setBackgroundResource(R.drawable.circle_pay_select);
            pay_check_box_wx.setBackgroundResource(R.drawable.circle_pay_unselect);
        }else if(wx){
            pay_check_box_ali.setBackgroundResource(R.drawable.circle_pay_unselect);
            pay_check_box_wx.setBackgroundResource(R.drawable.circle_pay_select);
        }
    }

    //商品详情确认订单和提交订单
    public void goos_Order2(String user_id, String goods_id, String address_id, String goods_num, String remark) {
        Api.goos_Order2(user_id, goods_id, address_id, goods_num, remark, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    GoodsOrder2Been gob = JSON.parseObject(apiResponse.getData(), GoodsOrder2Been.class);
                    WXPayUtils wxPayUtils = new WXPayUtils(PayReadyActivity.this, Constant.WXNOTIFY_URL);
                    wxPayUtils.pay("购物", cob.getTotalPrice(), gob.getOrder_sn());
                    finish();
                } else {
                }
            }
        });
    }

    //钻石订单生成接口
    public void Recharge(String user_id, String lemon_id) {
        Api.Recharge(user_id, lemon_id, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    RechargeBeen rb = JSON.parseObject(apiResponse.getData(), RechargeBeen.class);
                    WXPayUtils wxPayUtils = new WXPayUtils(PayReadyActivity.this, Constant.WXRECHARGENOTIFY_URL);
                    wxPayUtils.pay("充值", rb.getOrder_price(), rb.getOrder_sn());
                    finish();
                } else {
                }
            }
        });
    }
}
