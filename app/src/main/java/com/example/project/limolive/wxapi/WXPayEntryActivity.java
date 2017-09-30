package com.example.project.limolive.wxapi;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.localalbum.ui.BaseActivity;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.Constant;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2016/11/11.
 */

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;//得到api id
    private String orderId;//订单号

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);
        api.handleIntent(getIntent(), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    /**
     * 得到支付结果回调
     */
    @Override
    public void onResp(BaseResp resp) {
        int errCode = resp.errCode;
        switch (errCode) {
            case 0:
                ContentValues values = new ContentValues();
                getPersonalInfo();
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                break;
            case -1:
                Toast.makeText(this, "支付错误", Toast.LENGTH_LONG).show();
                break;
            case -2:
                Toast.makeText(this, "支付取消", Toast.LENGTH_LONG).show();
                break;
        }



        finish();//这里重要，如果没有 finish（）；将留在微信支付后的界面.
    }

    private void getPersonalInfo() {
       String order_sn=LiveingActivity.Order_sn;
        if (!"".equals(order_sn)&&order_sn!=null){
            Log.i("充值","名字：order_sn="+order_sn);
            Api.live_recharge_pp(order_sn, new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("充值",apiResponse.toString());
                }

                @Override
                public void onFailure(String errMessage) {
                    Log.i("充值","err="+errMessage.toString());
                }
            });

        }
    }

}
