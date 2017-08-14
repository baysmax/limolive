package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.NewAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.NewLiveBean;
import com.example.project.limolive.helper.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/14.
 */

public class NewFragment extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<NewLiveBean> newLiveList;
    private int page=1;
    private GridLayoutManager gm;
    private NewAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_new, inflater, container);
    }
    @Override
    protected void initView() {
        super.initView();
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.srl_DownNew);
        recyclerView= (RecyclerView) findViewById(R.id.rv_new);
        gm=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(gm);
        newLiveList= new ArrayList<>();
        initDate();
        adapter=new NewAdapter(newLiveList,getActivity());
        recyclerView.setAdapter(adapter);
        initListener();
    }

    private void initDate() {
        getData();
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                swipeRefreshLayout.setRefreshing(true);
                newLiveList.clear();
                getData();
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int positon = gm.findLastVisibleItemPosition();
                if (adapter!=null&&positon==adapter.getItemCount()-1&&newState==RecyclerView.SCROLL_STATE_IDLE){
                    page++;
                    getData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void getData() {
        Api.newsDate(LoginManager.getInstance().getUserID(getActivity()), new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    String data = apiResponse.getData();
                    newLiveList.addAll(JSONArray.parseArray(data, NewLiveBean.class));
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
