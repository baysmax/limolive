package com.example.project.limolive.activity;

import android.os.Bundle;
import android.view.View;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.AfterSaleFragment;
import com.example.project.limolive.fragment.AfterSaleFragments;
import com.example.project.limolive.fragment.ShopCartFragment;

/**
 * Created by AAA on 2017/8/28.
 */

public class AfterSaleActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale);
        initView();
        String type = getIntent().getStringExtra("type");
        if ("0".equals(type)){

            AfterSaleFragment mAfterSaleFragment=new AfterSaleFragment();
            loadFragment(mAfterSaleFragment,R.id.fragment_container);
        }else {
            AfterSaleFragments mAfterSaleFragment=new AfterSaleFragments();
            loadFragment(mAfterSaleFragment,R.id.fragment_container);
        }
    }

    private void initView() {
        loadTitle();
    }

    private void loadTitle() {
        setTitleString("售后管理");
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfterSaleActivity.this.finish();
            }
        });
    }
}
