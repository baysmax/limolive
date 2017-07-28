package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
public class bindPhoneActivity extends BaseActivity {
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
                ToastUtils.showCustom(bindPhoneActivity.this,"绑定成功", Toast.LENGTH_SHORT);
                bindPhoneActivity.this.finish();
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
}
