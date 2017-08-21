package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.limolive.R;

/**
 * Created by AAA on 2017/8/21.
 */

public class MallFragment extends BaseFragment implements View.OnClickListener{
    private LinearLayout ll_shops1,ll_shops2,ll_shops3;
    private ImageView iv_shops1,iv_shops2,iv_shops3;
    private TextView tv_shop_names1,tv_shop_names2,tv_shop_names3,tv_Price1,tv_Price2,tv_Price3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_mall,inflater,container);
    }

    @Override
    protected void initView() {
        super.initView();
        initViews();
        initListener();
        initData();
    }

    private void initData() {

    }

    private void initListener() {
        ll_shops1.setOnClickListener(this);
        ll_shops2.setOnClickListener(this);
        ll_shops3.setOnClickListener(this);
    }

    private void initViews() {
        ll_shops1= (LinearLayout) findViewById(R.id.ll_shops1);
        ll_shops2= (LinearLayout) findViewById(R.id.ll_shops2);
        ll_shops3= (LinearLayout) findViewById(R.id.ll_shops3);

        iv_shops1= (ImageView) findViewById(R.id.iv_shops1);
        iv_shops2= (ImageView) findViewById(R.id.iv_shops2);
        iv_shops3= (ImageView) findViewById(R.id.iv_shops3);

        tv_shop_names1= (TextView) findViewById(R.id.tv_shop_names1);
        tv_shop_names2= (TextView) findViewById(R.id.tv_shop_names2);
        tv_shop_names3= (TextView) findViewById(R.id.tv_shop_names3);

        tv_Price1= (TextView) findViewById(R.id.tv_Price1);
        tv_Price2= (TextView) findViewById(R.id.tv_Price2);
        tv_Price3= (TextView) findViewById(R.id.tv_Price3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_shops1:
                break;
            case R.id.ll_shops2:
                break;
            case R.id.ll_shops3:
                break;
        }

    }
}
