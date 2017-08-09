package com.example.project.limolive.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.RechargeBeen;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.pay.wx.WXPayUtils;
import com.example.project.limolive.tencentlive.views.MyAccountActivity;
import com.example.project.limolive.utils.Constant;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * Created by AAA on 2017/8/9.
 */

public class MyWalletActivity extends BaseActivity implements View.OnClickListener{
   // private RelativeLayout linear_zhuanshi;
    //private RelativeLayout linear_NMB;
    private ImageView title_back_button;
    //private TextView account_balance_num;
    private TextView[] textView;
    private int Numbers=0;
    private Button btn_zhifu;
    TextView tv_number,tv_nmb_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        initView();
        initListener();
        getZuanshi();
        getMemberCoins();

    }

    private void initListener() {
        title_back_button.setOnClickListener(this);
        btn_zhifu.setOnClickListener(this);
        textView[0].setOnClickListener(this);
        textView[1].setOnClickListener(this);
        textView[2].setOnClickListener(this);
        textView[3].setOnClickListener(this);
        textView[4].setOnClickListener(this);
        textView[5].setOnClickListener(this);
    }

    private void initView() {
        tv_nmb_number= (TextView) findViewById(R.id.tv_nmb_number);
        tv_number= (TextView) findViewById(R.id.tv_number);
        textView=new TextView[6];
        //account_balance_num= (TextView) findViewById(R.id.account_balance_num);
    //    linear_zhuanshi= (RelativeLayout) findViewById(R.id.linear_zhuanshi);
//        linear_NMB= (RelativeLayout) findViewById(R.id.linear_NMB);
        title_back_button= (ImageView) findViewById(R.id.title_back_button);
        btn_zhifu= (Button) findViewById(R.id.btn_zhifu);
        textView[0]= (TextView) findViewById(R.id.btn_charge_liushi);//60
        textView[1]= (TextView) findViewById(R.id.btn_charge_sanbai);//300
        textView[2]= (TextView) findViewById(R.id.btn_charge_yiqian);//1000
        textView[3]= (TextView) findViewById(R.id.btn_charge_liangqian);//2000
        textView[4]= (TextView) findViewById(R.id.btn_charge_wuqian);//5000
        textView[5]= (TextView) findViewById(R.id.btn_charge_yiwan);//10000
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.linear_zhuanshi:
// Intent sendPres = new Intent(MyWalletActivity.this, MyAccountActivity.class);
//                startActivity(sendPres);
//                break;
//            case R.id.linear_NMB:
//                break;
            case R.id.title_back_button:
               MyWalletActivity.this.finish();
               break;
            case R.id.btn_charge_liushi:
                setDraws();
                textView[0].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                btn_zhifu.setText("微信支付 ￥6.00");
                Numbers=1;
                break;
            case R.id.btn_charge_sanbai:
                setDraws();
                textView[1].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                Numbers=2;
                btn_zhifu.setText("微信支付 ￥30.00");
                break;
            case R.id.btn_charge_yiqian:
                setDraws();
                textView[2].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                Numbers=3;
                btn_zhifu.setText("微信支付 ￥100.00");
                break;
            case R.id.btn_charge_liangqian:
                setDraws();
                textView[3].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                Numbers=4;
                btn_zhifu.setText("微信支付 ￥200.00");
                break;
            case R.id.btn_charge_wuqian:
                setDraws();
                textView[4].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                Numbers=5;
                btn_zhifu.setText("微信支付 ￥500.00");
                break;
            case R.id.btn_charge_yiwan:
                setDraws();
                textView[5].setBackgroundDrawable(getDrawable(R.drawable.btns_bgf));
                Numbers=6;
                btn_zhifu.setText("微信支付 ￥1000.00");
                break;
            case R.id.btn_zhifu:
                Recharge(LoginManager.getInstance().getUserID(MyWalletActivity.this),String.valueOf(Numbers));
                break;
        }

    }
    private void getMemberCoins(){
        if (!NetWorkUtil.isNetworkConnected(MyWalletActivity.this)) {
            ToastUtils.showShort(MyWalletActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.getMemberCoins(LoginManager.getInstance().getUserID(MyWalletActivity.this), new ApiResponseHandler(MyWalletActivity.this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    if (apiResponse.getCode()==Api.SUCCESS){
                        String data = apiResponse.getData();
                        JSONObject parse = (JSONObject) JSON.parse(data);
                        String charm = parse.getString("charm");
                        Log.i("钻石","diamonds_coins:"+charm);
                        tv_nmb_number.setText(charm + "");
                    }
                }
            });
        }

    }
    //钻石订单生成接口
    private void Recharge(String user_id, String lemon_id) {
        Api.Recharge(user_id, lemon_id, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    RechargeBeen rb = JSON.parseObject(apiResponse.getData(), RechargeBeen.class);
                    WXPayUtils wxPayUtils = new WXPayUtils(MyWalletActivity.this, Constant.WXRECHARGENOTIFY_URL);
                    wxPayUtils.pay("充值", rb.getOrder_price(), rb.getOrder_sn());
                } else {
                    ToastUtils.showShort(MyWalletActivity.this,"请选择一个价格");
                }
            }
        });
    }

    private void setDraws() {
        Numbers=0;
        for (int i = 0; i < textView.length; i++) {
            textView[i].setBackgroundDrawable(getDrawable(R.drawable.btns_bg));
        }
    }

    private void getZuanshi(){
        if (!NetWorkUtil.isNetworkConnected(MyWalletActivity.this)) {
            ToastUtils.showShort(MyWalletActivity.this, NET_UNCONNECT);
            return;
        }else {
            Api.getDiamonds(LoginManager.getInstance().getUserID(MyWalletActivity.this),
                    new ApiResponseHandler(MyWalletActivity.this) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            Log.i("钻石","apiResponse="+apiResponse.toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                String data = apiResponse.getData();
                                JSONObject parse = (JSONObject) JSON.parse(data);
                                String diamonds_coins = parse.getString("diamonds_coins");
                                Log.i("钻石","diamonds_coins:"+diamonds_coins);
                                tv_number.setText(diamonds_coins + "");
                            }
                        }
            });
        }

    }

}
