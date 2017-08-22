package com.example.project.limolive.fragment;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.SearchActivity;
import com.example.project.limolive.adapter.New_Adapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.taowu.RecommendBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/21.
 */

public class New_Fragment extends BaseFragment {
    private SwipeRefreshLayout srl_down_new;
    private RecyclerView rv_new;
    private List<RecommendBean> new_list;
    private New_Adapter adapter;
    private GridLayoutManager gm;
    private int page=1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment__new,inflater,container);
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
        initData();
        initListener();
    }

    private void initListener() {
        srl_down_new.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_dark);
        srl_down_new.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_down_new.setRefreshing(true);
                page=1;
                initData();
            }
        });
        rv_new.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int positon = gm.findLastVisibleItemPosition();
                if (adapter!=null&&positon==adapter.getItemCount()-1&&newState==RecyclerView.SCROLL_STATE_IDLE){
                    page++;
                    initData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initData() {
        srl_down_new.setRefreshing(true);
        Api.getGoods(LoginManager.getInstance().getUserID(getActivity()), page, new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("获取普通商品","apiResponse"+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    srl_down_new.setRefreshing(false);
                    new_list.clear();
                    new_list.addAll(JSON.parseArray(apiResponse.getData(),RecommendBean.class));
                    adapter.notifyDataSetChanged();
                } else {
                    srl_down_new.setRefreshing(false);
                    ToastUtils.showShort(getActivity(), apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                srl_down_new.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView() {
        new_list=new ArrayList<>();

        srl_down_new= (SwipeRefreshLayout) findViewById(R.id.srl_down_new);
        rv_new= (RecyclerView) findViewById(R.id.rv_new);
        gm=new GridLayoutManager(getActivity(),2);
        rv_new.setLayoutManager(gm);
        adapter=new New_Adapter(getActivity(),new_list);
        rv_new.setAdapter(adapter);
    }





}
