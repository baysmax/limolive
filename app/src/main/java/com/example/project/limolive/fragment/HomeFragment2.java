package com.example.project.limolive.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.OrderActivity;
import com.example.project.limolive.activity.RankActivity;
import com.example.project.limolive.activity.SearchActivity;

import java.lang.reflect.Field;

/**
 * Created by AAA on 2017/8/14.
 */

public class HomeFragment2 extends BaseFragment implements View.OnClickListener{
    ViewPager viewPager;
    private BaseFragment fragments[];
    private TextView tabs[];
    private String tab[];
    private ImageView iv_search,iv_Phb;
    /**
     * 滑动TabLayout
     */
    private TabLayout tl_tab_layouts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_shouyes, inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        iv_Phb= (ImageView) findViewById(R.id.iv_Phb);
        iv_Phb.setOnClickListener(this);
        iv_search= (ImageView) findViewById(R.id.iv_search);
        iv_search.setOnClickListener(this);
        tl_tab_layouts= (TabLayout) findViewById(R.id.tl_tab_layouts);
        tl_tab_layouts.setTabTextColors(Color.parseColor("#454545"),Color.parseColor("#a0fce13d"));
        viewPager= (ViewPager) findViewById(R.id.vp_fragment);
        loadTabLayout();

//        tabs[0]= (TextView) findViewById(R.id.tv_follow);
//        tabs[1]= (TextView) findViewById(R.id.tv_recommend);
//        tabs[2]= (TextView) findViewById(R.id.tv_new);


    }
    /**
     * 初始化Tab
     */
    private void loadTabLayout() {
        loadFragments();
        tab=getResources().getStringArray(R.array.sy_type);
        for(int i=0;i<tab.length;i++){
            TabLayout.Tab TabLayouttab=tl_tab_layouts.newTab();
            TabLayouttab.setText(tab[i]);
            tl_tab_layouts.addTab(TabLayouttab);
            tl_tab_layouts.post(new Runnable() {
                @Override
                public void run() {
                    setIndicator(tl_tab_layouts,2,2);
                    tl_tab_layouts.setSelectedTabIndicatorHeight(2);
                }
            });
        }
        initViewPager();
    }
    FragmentAdapter pagerAdapter;

    private void initViewPager() {
        pagerAdapter=new FragmentAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tl_tab_layouts.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }

    private void loadFragments() {
        fragments=new BaseFragment[3];
        fragments[0]=new FollowFragment();
        fragments[1]=new HomeFragment();
        fragments[2]=new NewFragment();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_Phb:
                Intent intent = new Intent();
                intent.setClass(getContext(),RankActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_search:
                Intent intent1=new Intent();
                intent1.setClass(getContext(),SearchActivity.class);
                startActivity(intent1);
                break;
        }
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
            return tab[position];
        }
    }

    /**
     * 设置tablayout下划线的宽度
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public void setIndicator (TabLayout tabs,int leftDip,int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}
