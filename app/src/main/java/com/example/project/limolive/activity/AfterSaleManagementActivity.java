package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.project.limolive.R;

/**
 * @author 黄亚菲 on 2017/1/6.
 * 售后管理
 */
public class AfterSaleManagementActivity extends BaseActivity implements View.OnClickListener{

    //我的售后记录
    private RelativeLayout rl_after_sale_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_management);

        initView();
        monitor();
    }

    private void initView(){
        loadTitle();
        rl_after_sale_num = (RelativeLayout) findViewById(R.id.rl_after_sale_num);
    }

    private void monitor(){
        rl_after_sale_num.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.rl_after_sale_num:
                break;
        }
    }


    private void loadTitle() {
        setTitleString(getString(R.string.after_sale_management));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
