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
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import static com.example.project.limolive.presenter.ShopCartPresenter.CART_LIST_OVER;

/**
 * Created by AAA on 2017/8/28.
 */

public class AfterSaleFragments extends BaseFragment implements Presenter.NotificationToActivity{
    private MyListView listView;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    private OrderPresenters1 presenter;
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
        listView = (MyListView) findViewById(R.id.listView);
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
                presenter.getOrder(LoginManager.getInstance().getUserID(getActivity()), String.valueOf(page),"0");
            }
        });
        listView.setiLoadListener(new MyListView.ILoadListener() {
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
    static class MyListView extends ListView  implements AbsListView.OnScrollListener {
        private View footer;//底部布局
        private int totalItemCount;//总数量
        private int lastVisibleItem;//最后一个可见的Item
        private boolean isLoading;//正在加载


        public void setiLoadListener(ILoadListener iLoadListener) {
            this.iLoadListener = iLoadListener;
        }

        ILoadListener iLoadListener;
        public MyListView(Context context) {
            super(context);
            initView(context);
        }

        public MyListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView(context);
        }

        public MyListView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initView(context);
        }
        /**
         * 添加底部加载提示布局到listview
         * @param context
         */
        private void initView(Context context) {
//            LayoutInflater layoutInflater=LayoutInflater.from(context);
//            footer=layoutInflater.inflate(R.layout.lx_footer_layout, null);
//            footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
//            this.addFooterView(footer);
//            this.setOnScrollListener(this);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        {
            this.lastVisibleItem=firstVisibleItem+visibleItemCount;
            this.totalItemCount=totalItemCount;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (totalItemCount==lastVisibleItem&&scrollState==SCROLL_STATE_IDLE) {
                if (!isLoading) {
                    isLoading=true;
                    //footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
                    iLoadListener.onLoad();//加载更多数据
                }
            }
        }
        //加载完毕
        public void loadComplete() {
            isLoading=false;
            //footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
        }
        public void setInterface(ILoadListener iLoadListener) {
            this.iLoadListener=iLoadListener;
        }
        //加载更多数据的回调接口
        public interface ILoadListener
        {
            void onLoad();
        }
    }
}
