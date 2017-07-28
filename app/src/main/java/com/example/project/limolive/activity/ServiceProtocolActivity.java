package com.example.project.limolive.activity;

import android.os.Bundle;
import android.view.View;

import com.example.project.limolive.R;

/**
 * 服务协议
 * @author hwj on 2016/12/14.
 */

public class ServiceProtocolActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_protocol);
        initView();
    }

    private void initView() {
        loadTitle();
    }

    private void loadTitle() {
        setTitleString(getString(R.string.login_protocol_title));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceProtocolActivity.this.finish();
            }
        });
    }
}
