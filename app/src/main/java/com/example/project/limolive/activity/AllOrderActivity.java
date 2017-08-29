package com.example.project.limolive.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.example.project.limolive.R;
import com.example.project.limolive.fragment.AllOrderFragment;
import com.example.project.limolive.fragment.AllOrderFragments;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.fragment.RefundFragment;
import com.example.project.limolive.fragment.ShippedFragment;
import com.example.project.limolive.fragment.ShopCartFragment;
import com.example.project.limolive.fragment.WaitCommentFragment;
import com.example.project.limolive.fragment.WaitCommentFragments;
import com.example.project.limolive.fragment.WaitPayFragment;
import com.example.project.limolive.fragment.WaitReceiveFragment;
import com.example.project.limolive.fragment.WaitSendFragment;
import com.example.project.limolive.fragment.WaitSendFragments;

/**
 * Created by AAA on 2017/8/26.
 */

public class AllOrderActivity extends BaseActivity{
    /**
     * 滑动TabLayout
     */
    private XTabLayout tl_tab_layout;
    /**
     * 订单fragments
     */
    private BaseFragment fragments[];
    private String tabs[];

    private ViewPager vp_fragments;
    private FragmentAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_all_order);
        initView();
    }
    private void initView() {
        loadTitle();

        tl_tab_layout= (XTabLayout) findViewById(R.id.tl_tab_layout);
        tl_tab_layout.setTabTextColors(Color.parseColor("#454545"),STATUS_BAR_COLOR_DEFAULT);
        vp_fragments= (ViewPager) findViewById(R.id.vp_fragments);
        loadTabLayout();
    }
    /**
     * 初始化Tab
     */
    private void loadTabLayout() {
        loadFragments();
        tabs=getResources().getStringArray(R.array.order_types);
        for(int i=0;i<tabs.length;i++){
            XTabLayout.Tab tab = tl_tab_layout.newTab();
            tab.setText(tabs[i]);
            tl_tab_layout.addTab(tab);
        }
        initViewPager();
    }
    private void initViewPager() {

        pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        vp_fragments.setAdapter(pagerAdapter);
        tl_tab_layout.setupWithViewPager(vp_fragments);

        int page=getIntent().getIntExtra("type",0);
        vp_fragments.setCurrentItem(page);
    }
    private void loadTitle() {
        setTitleString("交易订单");
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllOrderActivity.this.finish();
            }
        });
    }
    private void loadFragments() {
        fragments=new BaseFragment[3];
        fragments[0]=new AllOrderFragments();//全部
        fragments[1]=new WaitSendFragments();//待发货
        //fragments[2]=new ShippedFragment();//已发货
        //fragments[2]=new RefundFragment();//退款中
        fragments[2]=new WaitCommentFragment();//已完成

    }
    class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
