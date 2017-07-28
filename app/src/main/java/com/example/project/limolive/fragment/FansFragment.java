package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.presenter.FansAndAttention;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import static com.example.project.limolive.presenter.FansAndAttention.UPDATE_OVER;

/**
 * 粉丝
 * @author hwj on 2016/12/19.
 */

public class FansFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        Presenter.NotificationToActivity {

    private AutoSwipeRefreshLayout swipe_list;
    private FansAndAttention presenter;

    private ListView lv_info_list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_fans,inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        presenter=new FansAndAttention(getActivity());
        presenter.registerMsgToActivity(this);
        loadTitle();

        swipe_list= (AutoSwipeRefreshLayout) findViewById(R.id.swipe_list);
        lv_info_list= (ListView) findViewById(R.id.lv_info_list);
        lv_info_list.setAdapter(presenter.getFansAdapter());
        swipe_list.setColorSchemeColors(BaseActivity.STATUS_BAR_COLOR_DEFAULT);
        swipe_list.setOnRefreshListener(this);
        swipe_list.autoRefresh();
    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_fans));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @Override
    public void presenterTakeAction(int action) {
        if(action==UPDATE_OVER){
            swipe_list.setRefreshing(false);
        }
    }
}
