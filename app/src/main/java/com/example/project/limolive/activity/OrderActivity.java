package com.example.project.limolive.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.AllOrderFragment;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.fragment.ReturnShopFragment;
import com.example.project.limolive.fragment.WaitCommentFragment;
import com.example.project.limolive.fragment.WaitPayFragment;
import com.example.project.limolive.fragment.WaitReceiveFragment;

/**
 * 订单页面
 * @author hwj on 2016/12/14.
 */

public class OrderActivity extends BaseActivity {


    /**
     * 滑动TabLayout
     */
    private TabLayout tl_tab_layout;
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
        setContentView(R.layout.activity_order);
        initView();
    }

    private void initView() {
        loadTitle();

        tl_tab_layout= (TabLayout) findViewById(R.id.tl_tab_layout);
        tl_tab_layout.setTabTextColors(Color.parseColor("#454545"),STATUS_BAR_COLOR_DEFAULT);
        vp_fragments= (ViewPager) findViewById(R.id.vp_fragments);
        loadTabLayout();
    }

    /**
     * 初始化Tab
     */
    private void loadTabLayout() {
        loadFragments();
        tabs=getResources().getStringArray(R.array.order_type);
        for(int i=0;i<tabs.length;i++){
            TabLayout.Tab tab=tl_tab_layout.newTab();
            tab.setText(tabs[i]);
            tl_tab_layout.addTab(tab);
        }
        initViewPager();
    }
    private void loadFragments() {
        fragments=new BaseFragment[5];
        fragments[0]=new AllOrderFragment();
        fragments[1]=new WaitPayFragment();
        fragments[2]=new WaitReceiveFragment();
        fragments[3]=new WaitCommentFragment();
        fragments[4]=new ReturnShopFragment();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {

        pagerAdapter=new FragmentAdapter(getSupportFragmentManager());
        vp_fragments.setAdapter(pagerAdapter);
        tl_tab_layout.setupWithViewPager(vp_fragments);

        int page=getIntent().getIntExtra("orderPage",0);
        vp_fragments.setCurrentItem(page);
    }


    private void loadTitle() {
        setTitleString(getString(R.string.order_title));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderActivity.this.finish();
            }
        });
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
