package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.ThridLogin;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.LoginPresenter;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

/**
 * 作者：hpg on 2017/3/17 18:31
 * 功能：
 */
public class SendPhoneActivity extends BaseActivity {
    private EditText phone_number;
    private Button buy_code;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();

        setContentView(R.layout.activity_sendphone);
        nitView();
    }

    private void nitView() {
        loadTitle();
        phone_number = (EditText) findViewById(R.id.phone_number);
        buy_code = (Button) findViewById(R.id.buy_code);
        buy_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //绑定手机号
                LoginThrid(phone_number.getText().toString(), intent.getStringExtra("nickname"), intent.getStringExtra("type"), intent.getStringExtra("openid"), intent.getStringExtra("headsmall"));
            }
        });
    }

    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        setTitleString("绑定");
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //第三方登录
    private void LoginThrid(final String phone, String nickname, String type, String openid, String headsmall) {
        if (NetWorkUtil.isNetworkConnected(this)) {
            Api.thirdLogin(phone, nickname, type, openid, headsmall, new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("第三方登录","注册成功"+"SendPhoneActivity..."+apiResponse.toString());
                    if (apiResponse.getCode() == 0) {
                        ToastUtils.showShort(SendPhoneActivity.this, "成功");
                        ThridLogin thridLogin = JSON.parseObject(apiResponse.getData(), ThridLogin.class);
                        Log.i("第三方登录","注册成功"+"SendPhoneActivity.thridLogin.."+ thridLogin.toString());

                        LoginPresenter loginPresenter = new LoginPresenter(SendPhoneActivity.this);
                        LiveMySelfInfo.getInstance().setPhone(phone);
                       // JSONObject jsonObject = JSON.parseObject(apiResponse.getData());
                        LiveMySelfInfo.getInstance().setId(thridLogin.getUid());
                        LoginManager.getInstance().setUserID(SendPhoneActivity.this,thridLogin.getUid());
                        LiveMySelfInfo.getInstance().setNickName(thridLogin.getUid());
                        LiveMySelfInfo.getInstance().setAvatar(thridLogin.getHeadsmall());
                        loginPresenter.cashData(apiResponse.getData());
                        loginPresenter.getUserSig(phone);
                    } else {
                        ToastUtils.showShort(SendPhoneActivity.this, apiResponse.getMessage());
                    }
                }
            });

        } else {
            ToastUtils.showShort(SendPhoneActivity.this, "网络异常，请检查您的网络~");
        }
    }

}
