package com.example.project.limolive.presenter;
/**
 * Created by Administrator on 2016/12/14.
 */

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AbsListView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.HomeAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * HomePresenter
 * 作者：李志超 on 2016/12/14 16:50
 */

public class HomePresenter extends Presenter implements AbsListView.OnScrollListener {
    public static final int FRIEND_FORUM_LIST = 0;
    public int page = 1;
    private List<HomeListBeen> homeListBeens;
    private HomeAdapter homeAdapter;
    private int visibleLastIndex = 0;   //最后的可视项索引
    private boolean isAdd = false; //是否需要追加数据

    public HomePresenter(Context context) {
        super(context);
    }

    public HomeAdapter getHomeAdapter() {
        if (homeAdapter == null) {
            homeListBeens = new ArrayList<HomeListBeen>();
            homeAdapter = new HomeAdapter(context, homeListBeens);


        }
        return homeAdapter;
    }

    @Override
    protected void getDate(final boolean isClear) {
        super.getDate(isClear);
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            if (tellActivity != null) {
                tellActivity.presenterTakeAction(FRIEND_FORUM_LIST);
            }
            return;
        }

        Api.liveList(String.valueOf(page), new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {

                    Log.i("直播列表",apiResponse.toString());
                    handle(isClear, apiResponse.getData());
                } else {
                    //ToastUtils.showShort(context, apiResponse.getMessage());
                    Log.i("main",apiResponse.getMessage());
                }
                if (tellActivity != null) {
                    tellActivity.presenterTakeAction(FRIEND_FORUM_LIST);
                }
                if (swipeRefreshLayout!=null){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    public List<HomeListBeen> getListBean() {
        return homeListBeens;
    }

    /**
     * 进行json处理
     *
     * @param isClear 是否清空数据
     * @param data    json
     */
    private void handle(boolean isClear, String data) {
        if (TextUtils.isEmpty(data)) {
            Log.i(TAG, getString(R.string.json_is_null));
            return;
        }
        List<HomeListBeen> list = JSON.parseArray(data, HomeListBeen.class);
        if (isClear) {
            homeListBeens.clear();
        }
        /*网络数据为10条，实际显示9条，少一条,归根结底其实还要从复用机制入手解决*/

        homeListBeens.addAll(list);
        Log.i("homeListBeensss",homeListBeens.toString());

        homeAdapter.notifyDataSetChanged();
        isAdd = true;
    }

    public void refresh() {
        page = 1;
        getDate(true);
    }
    SwipeRefreshLayout swipeRefreshLayout;
    public void refresh(SwipeRefreshLayout swipeRefreshLayout) {
        page = 1;
        this.swipeRefreshLayout=swipeRefreshLayout;
        getDate(true);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.i("onScrollStateChanged---", "true");
        int itemsLastIndex = homeAdapter.getCount();
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex - 1 == itemsLastIndex) {
            //如果是自动加载,可以在这里放置异步加载数据的代码
            if (isAdd) {
                isAdd = false;
                page++;
                getDate(false);
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
        if (visibleLastIndex >= totalItemCount) {
            isAdd = true;
        }
    }
}
