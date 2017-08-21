package com.example.project.limolive.activity;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.fragment.CategoryFragment;
import com.example.project.limolive.fragment.FindGoodsFragment;
import com.example.project.limolive.fragment.New_Fragment;
import com.example.project.limolive.fragment.ShoppingFragment;

import static com.example.project.limolive.R.id.ll_tabs;

/**
 * Created by AAA on 2017/8/21.
 */

public class ShoppingActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout ll_mall,ll_category,ll_new,ll_shopping;
    private TextView tv_mall,tv_category,tv_new,tv_shopping;
    private ImageView iv_mall,iv_category,iv_new,iv_shopping;
    private BaseFragment[] fragments;
    private int clickIndex=0;
    private int currentIndex=0;
    private TextView[] tab_tv;
    private ImageView tabs[];

    private TextView title,back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        loadTitle();
        initView();
        initListener();
        initFragment();

    }

    private void loadTitle() {
        title= (TextView) findViewById(R.id.title);
        back= (TextView) findViewById(R.id.menu_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingActivity.this.finish();
            }
        });
        title.setText("柠檬商城");
    }

    private void initFragment() {
        fragments=new BaseFragment[4];
        fragments[0]=new FindGoodsFragment();
        fragments[1]=new CategoryFragment();
        fragments[2]=new New_Fragment();
        fragments[3]=new ShoppingFragment();
        loadFragment();

    }

    private void initListener() {
        ll_mall.setOnClickListener(this);
        ll_category.setOnClickListener(this);
        ll_new.setOnClickListener(this);
        ll_shopping.setOnClickListener(this);
    }

    private void initView() {
        ll_mall= (LinearLayout) findViewById(R.id.ll_mall);
        ll_category= (LinearLayout) findViewById(R.id.ll_category);
        ll_new= (LinearLayout) findViewById(R.id.ll_new);
        ll_shopping= (LinearLayout) findViewById(R.id.ll_shopping);

        tv_mall= (TextView) findViewById(R.id.tv_mall);
        tv_category= (TextView) findViewById(R.id.tv_category);
        tv_new= (TextView) findViewById(R.id.tv_new);
        tv_shopping= (TextView) findViewById(R.id.tv_shopping);

        tab_tv=new TextView[4];
        tab_tv[0]=tv_mall;
        tab_tv[1]=tv_category;
        tab_tv[2]=tv_new;
        tab_tv[3]=tv_shopping;

        iv_mall= (ImageView) findViewById(R.id.iv_mall);
        iv_category= (ImageView) findViewById(R.id.iv_category);
        iv_new= (ImageView) findViewById(R.id.iv_new);
        iv_shopping= (ImageView) findViewById(R.id.iv_shopping);

        tabs=new ImageView[4];
        tabs[0]=iv_mall;
        tabs[1]=iv_category;
        tabs[2]=iv_new;
        tabs[3]=iv_shopping;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_mall:
                clickIndex=0;
                break;
            case R.id.ll_category:
                clickIndex=1;
                break;
            case R.id.ll_new:
                clickIndex=2;
                break;
            case R.id.ll_shopping:
                clickIndex=3;
                break;

        }
        loadFragment();
    }
    /**
     * 加载Fragment R.id.framelayouts color_57DDC5
     */
    private void loadFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragments[clickIndex].isAdded()) {
            ft.add(R.id.framelayouts, fragments[clickIndex]);
        }

        ft.hide(fragments[currentIndex]);
        ft.show(fragments[clickIndex]);
        changeStyle();
        currentIndex = clickIndex;
        ft.commit();
    }
    /**
     * 修改图片颜色
     */
    private void changeStyle() {
        if (currentIndex == 0) {
            tabs[0].setSelected(false);
            tab_tv[0].setTextColor(0x4b4a4a);
        }
        if (clickIndex == 0) {
            tabs[0].setSelected(true);
            tab_tv[0].setTextColor(0xa0fce13d);
        }
        tabs[currentIndex].setSelected(false);
        for (int i = 0; i < tab_tv.length; i++) {
            tab_tv[i].setTextColor(0xaa4b4a4a);
        }
        //tab_tv[currentIndex].setTextColor(0x4b4a4a);
        tabs[clickIndex].setSelected(true);
        tab_tv[clickIndex].setTextColor(0xa0fce13d);
    }
}
