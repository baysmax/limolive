package com.example.project.limolive.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.OrderPresenter;
import com.example.project.limolive.presenter.OrderPresenters;
import com.example.project.limolive.presenter.OrderPresenters1;
import com.example.project.limolive.presenter.OrderPresenters2;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.view.MyListViews;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import static com.example.project.limolive.presenter.ShopCartPresenter.CART_LIST_OVER;

/**
 * Created by AAA on 2017/8/28.
 */

public class AfterSaleFragments extends BaseFragment implements Presenter.NotificationToActivity{
    private MyListViews listView;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    private OrderPresenters2 presenter;
    private TextView tv;
    private View view;
    private int page=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return setContentView(R.layout.fragment_all_order,inflater,container);
        view = setContentView(R.layout.fragment_after_sale, inflater, container);
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        listView = (MyListViews) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.tv);
        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        presenter = new OrderPresenters2(getActivity());
        presenter.registerMsgToActivity(this);
        presenter.setAdapter(listView);
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
        swipe_refresh_tool.autoRefresh();
        swipe_refresh_tool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                presenter.getOrder(LoginManager.getInstance().getUserID(getActivity()), String.valueOf(page),"0");
            }
        });
        listView.setiLoadListener(new MyListViews.ILoadListener() {
            @Override
            public void onLoad() {
                page++;
                presenter.getOrder(LoginManager.getInstance().getUserID(getActivity()), String.valueOf(page),"0");
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
