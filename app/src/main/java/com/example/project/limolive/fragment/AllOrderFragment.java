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
import com.example.project.limolive.presenter.OrderPresenter;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import static com.example.project.limolive.presenter.ShopCartPresenter.CART_LIST_OVER;


/**
 * 全部订单
 *
 * @author ZL on 2016/12/14.
 */

public class AllOrderFragment extends BaseFragment implements Presenter.NotificationToActivity {

    private ListView listView;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    private OrderPresenter presenter;
    private TextView tv;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return setContentView(R.layout.fragment_all_order,inflater,container);
        view = setContentView(R.layout.order, inflater, container);
        return view;
    }

    @Override
    protected void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        tv = (TextView) findViewById(R.id.tv);
        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        presenter = new OrderPresenter(getActivity());
        presenter.registerMsgToActivity(this);
        presenter.setAdapter(listView);
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
        swipe_refresh_tool.autoRefresh();
        swipe_refresh_tool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getOrder(LoginManager.getInstance().getUserID(getActivity()), "");
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
