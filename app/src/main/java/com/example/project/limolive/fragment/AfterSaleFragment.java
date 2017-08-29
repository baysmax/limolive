package com.example.project.limolive.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.OrderPresenters;
import com.example.project.limolive.presenter.OrderPresenters1;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import static com.example.project.limolive.presenter.ShopCartPresenter.CART_LIST_OVER;

/**
 * Created by AAA on 2017/8/28.
 */

public class AfterSaleFragment  extends BaseFragment implements Presenter.NotificationToActivity{
    private ListView listView;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    private OrderPresenters1 presenter;
    private TextView tv;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return setContentView(R.layout.fragment_all_order,inflater,container);
        view = setContentView(R.layout.fragment_after_sale, inflater, container);
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.tv);
        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        presenter = new OrderPresenters1(getActivity());
        presenter.registerMsgToActivity(this);
        presenter.setAdapter(listView);
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
        swipe_refresh_tool.autoRefresh();
        swipe_refresh_tool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getOrder(LoginManager.getInstance().getUserID(getActivity()), "1");
            }
        });
    }

    @Override
    public void presenterTakeAction(int action) {
        if (action == 1) {
            tv.setVisibility(View.VISIBLE);
        } else if (action == 2) {
            tv.setVisibility(View.GONE);
        } else if (action == CART_LIST_OVER) {
            swipe_refresh_tool.setRefreshing(false);
        }
    }
}
