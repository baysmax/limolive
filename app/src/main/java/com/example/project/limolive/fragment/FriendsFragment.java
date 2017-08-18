package com.example.project.limolive.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.project.limolive.R;
import com.example.project.limolive.tencentim.ui.ConversationActivity;
import com.example.project.limolive.tencentim.ui.SearchFriendActivity;

/**
 * Created by AAA on 2017/8/17.
 */

public class FriendsFragment extends BaseFragment implements View.OnClickListener{
    private BaseFragment fragments[];
    private FragmentAdapter pagerAdapter;
    private ViewPager viewPager;
    private ImageView iv_title;
    private FrameLayout fl_adds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_friends,inflater,container);
    }


    @Override
    protected void initView() {
        super.initView();
        iv_title= (ImageView) findViewById(R.id.iv_title);
        fl_adds= (FrameLayout) findViewById(R.id.fl_adds);
        fl_adds.setOnClickListener(this);
        iv_title.setOnClickListener(this);
        loadFragments();
        initViewPager();
    }
    private void loadFragments() {
        fragments=new BaseFragment[2];
        fragments[0]=new NewsFragment();
        fragments[1]=new FriendsStoreFragment();
    }
    private void initViewPager() {
        viewPager= (ViewPager) findViewById(R.id.vp_friends);
        pagerAdapter=new FragmentAdapter(getFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //页面滑动后
                pagerAdapter.setPosition(position);
                iv_title.setImageDrawable(getActivity().getDrawable(position==0?R.drawable.friend:R.drawable.friends));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title:
                int position = pagerAdapter.getPosition();
                viewPager.setCurrentItem(position==0?1:0,false);
                pagerAdapter.setPosition(position==0?1:0);
                break;
            case R.id.fl_adds:
                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
                intent.putExtra("fromWhere","AddFriend");
                getActivity().startActivity(intent);
                break;
        }
    }


    class FragmentAdapter extends FragmentStatePagerAdapter  {


        private int position;


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

    }
}
