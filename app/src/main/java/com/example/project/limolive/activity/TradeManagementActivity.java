package com.example.project.limolive.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.DealManageBean;
import com.example.project.limolive.fragment.AllOrderFragment;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

/**
 * @author 黄亚菲 on 2017/1/6.
 * 交易管理
 */

public class TradeManagementActivity extends BaseActivity implements View.OnClickListener {


    private Context context;
    private DealManageBean bean;

    //待付款 代发货  已发货  退款中   待评价  订单总额
    private TextView tv_storge_num,tv_send_num,tv_already_send_num,tv_reback_money_ing,tv_wait_for_comment,tv_orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_manegement);
        initView();
        initListener();
    }

    private void initListener() {
        tv_storge_num.setOnClickListener(this);
        tv_send_num.setOnClickListener(this);
        tv_already_send_num.setOnClickListener(this);
        tv_reback_money_ing.setOnClickListener(this);
        tv_wait_for_comment.setOnClickListener(this);
        tv_orders.setOnClickListener(this);



    }


    private void initView(){
        context = this;
        loadTitle();

        tv_storge_num = (TextView) findViewById(R.id.tv_storge_num);
        tv_send_num = (TextView) findViewById(R.id.tv_send_num);
        tv_reback_money_ing = (TextView) findViewById(R.id.tv_reback_money_ing);
        tv_already_send_num = (TextView) findViewById(R.id.tv_already_send_num);
        tv_wait_for_comment = (TextView) findViewById(R.id.tv_wait_for_comment);
        tv_orders = (TextView) findViewById(R.id.tv_orders);

        getTradeData(LoginManager.getInstance().getUserID(context));

    }


    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        setTitleString(getString(R.string.mine_trade_manager));
        setLeftImage(R.mipmap.icon_return);
        setRightImage(R.mipmap.fenlei);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


/*********
 *获取交易管理数据
 *
 * 参数名	类型	说明
 waitpay	string	待支付数
 waitsend	string	待发货数
 waitreceive	string	已发货
 waitcomment	string	待评价
 refunding	string	退款中
 order_num	string	订单总数
 */

    private void getTradeData(String uid){

        if (NetWorkUtil.isNetworkConnected(context)){

            Api.getTradeData(uid, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    if (apiResponse.getCode() == 0){
                        bean = JSON.parseObject(apiResponse.getData(),DealManageBean.class);

                        //待付款
                        tv_storge_num.setText(bean.getWaitpay()+"件");

                        //代发货
                        tv_send_num.setText(bean.getWaitsend()+"件");

                        //已发货
                        tv_already_send_num.setText(bean.getWaitreceive()+"件");

                        //退款中
                        tv_wait_for_comment.setText(bean.getRefunding()+"件");

                        //待评价
                        tv_reback_money_ing.setText(bean.getWaitcomment()+"件");

                        //订单总数
                        tv_orders.setText(bean.getOrder_num()+"件");









                    }else if (apiResponse.getCode() == -1){
                        ToastUtils.showShort(context,"没有此用户~");
                    }
                }
            });

        }else {
            ToastUtils.showShort(context,"网络异常，请检查您的网络~");
        }


    }

    /**
     *    //待付款 代发货  已发货  退款中   待评价  订单总额
     private TextView tv_storge_num,tv_send_num,tv_already_send_num,tv_reback_money_ing,tv_wait_for_comment,tv_orders;
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_storge_num://待付款
                //goOrder(0);

                break;
            case R.id.tv_send_num://待发货·
                goOrder(1);
                break;
            case R.id.tv_already_send_num://已发货
                //goOrder(2);
                break;
            case R.id.tv_reback_money_ing://已完成
                //goOrder(3);
                goOrder(2);
                break;
            case R.id.tv_wait_for_comment://已完成


                break;
            case R.id.tv_orders://全部·
                goOrder(0);
                break;

        }

    }

    private void goOrder(int i) {
        Intent intent = new Intent();
        intent.putExtra("type",i);
        intent.setClass(TradeManagementActivity.this,AllOrderActivity.class);
        startActivity(intent);
    }
}
