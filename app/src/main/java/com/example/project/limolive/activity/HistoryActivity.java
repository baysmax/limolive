package com.example.project.limolive.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.FootMarkAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.FootMarkBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 足迹
 * @author hwj on 2016/12/19.
 */

public class HistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    private List<FootMarkBean> listAll;
    private FootMarkAdapter adapter;
    private AutoSwipeRefreshLayout swipe_cart;
    private ListView lv_foot_marker_list;

    private TextView tv_non_data;
    private Context context;
    private int page = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initView();
    }

    private void initView() {
        context = this;
        loadTitle();
        tv_non_data = (TextView) findViewById(R.id.tv_non_data);
        tv_non_data.setVisibility(View.INVISIBLE);
        listAll = new ArrayList<>();

        swipe_cart = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_cart);
        lv_foot_marker_list = (ListView) findViewById(R.id.lv_foot_marker_list);
        swipe_cart.setRefreshing(true);
        swipe_cart.setColorSchemeColors(BaseActivity.STATUS_BAR_COLOR_DEFAULT);
        swipe_cart.setOnRefreshListener(this);
        swipe_cart.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page ++;
                footMarkData(LoginManager.getInstance().getUserID(context),page);
            }
        });

        footMarkData(LoginManager.getInstance().getUserID(context),page);

    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_look_history));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryActivity.this.finish();
            }
        });
        setRightText("清空足迹");
    }

    /***
     * 浏览足迹
     */
    private void footMarkData(String uid,int page){

        if (NetWorkUtil.isNetworkConnected(context)){

            Api.getFootMarkData(uid, page, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    if (apiResponse.getCode() == 0){

                        List<FootMarkBean> list = JSONArray.parseArray(apiResponse.getData(),FootMarkBean.class);

                        if (null != list){
                            tv_non_data.setVisibility(View.GONE);
                            listAll.clear();
                            listAll.addAll(list);
                            adapter = new FootMarkAdapter(context,listAll);
                            lv_foot_marker_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            swipe_cart.setRefreshing(false);
                        }
                    }else {
                        ToastUtils.showShort(context,apiResponse.getMessage());
                        swipe_cart.setRefreshing(false);
                    }
                }
            });

        }else {
            swipe_cart.setRefreshing(false);
            ToastUtils.showShort(context,"网络异常，请检查您的网络~");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!swipe_cart.isRefreshing()){
            swipe_cart.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        if (!swipe_cart.isRefreshing()){
            swipe_cart.autoRefresh();
        }
    }
}
