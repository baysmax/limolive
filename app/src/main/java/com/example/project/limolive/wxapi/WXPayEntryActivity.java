package com.example.project.limolive.wxapi;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project.limolive.localalbum.ui.BaseActivity;
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
//                if (!TextUtils.isEmpty(LoginManager.getInstance().getString(WXPayEntryActivity.this, "vip"))) {
                ContentValues values = new ContentValues();
                values.put("vip", "1");
//                    resolver.updateInfo(values, LoginManager.getInstance().getUserID(WXPayEntryActivity.this));
                getPersonalInfo();
//                    LoginManager.getInstance().setString(WXPayEntryActivity.this, "vip", "");
//                }
                Toast.makeText(this, "支付成功", Toast.LENGTH_LONG).show();
                break;
            case -1:
//                LoginManager.getInstance().setString(WXPayEntryActivity.this, "vip", "");
                Toast.makeText(this, "支付错误", Toast.LENGTH_LONG).show();
                break;
            case -2:
//                LoginManager.getInstance().setString(WXPayEntryActivity.this, "vip", "");
                Toast.makeText(this, "支付取消", Toast.LENGTH_LONG).show();
                break;
        }

//        Intent intent = new Intent(PayActivity.PAY_RESULT);//返回App界面
//        intent.putExtra("err_code",errCode+"");
//        sendBroadcast(intent);
        Log.e("TAG", "errCodes" + errCode);

        finish();//这里重要，如果没有 finish（）；将留在微信支付后的界面.
    }

    private void getPersonalInfo() {
//        if (NetWorkUtils.isConnected(this)) {
//            Api.healthRecord(LoginManager.getInstance().getUserID(WXPayEntryActivity.this), new ApiResponseHandler(WXPayEntryActivity.this) {
//                @Override
//                public void onSuccess(ApiResponse apiResponse) {
//                    if (apiResponse.getCode() == 0) {
//                        PersonInfo info = JSON.parseObject(apiResponse.getData(), PersonInfo.class);
//                        ContentValues values = new ContentValues();
//                        values.put("vip_start", info.getVip_start());
//                        values.put("vip_end", info.getVip_end());
//                        resolver.updateInfo(values, LoginManager.getInstance().getUserID(WXPayEntryActivity.this));
//                    } else {
//                        ToastUtils.showToast(apiResponse.getMessage());
//                    }
//                }
//            });
//        } else {
//            ToastUtils.showToast("网络不可用");
//        }
    }

}
