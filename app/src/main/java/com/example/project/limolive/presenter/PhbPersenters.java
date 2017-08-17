package com.example.project.limolive.presenter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.adapter.PHBadapter;
import com.example.project.limolive.adapter.PHBadapters;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.phb.PHBean;
import com.example.project.limolive.bean.phb.PHBeans;
import com.example.project.limolive.helper.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/2.
 */

public class PhbPersenters extends Presenter{
    private Context context;
    private List<PHBeans> list;
    private RecyclerView recyclerView;
    private PHBadapters adapter;
    private LinearLayoutManager manager;

    public PhbPersenters(Context context) {
        super(context);
        this.context=context;
    }
    private boolean result=false;
    public Boolean getDate(){

        Api.getRankings(LoginManager.getInstance().getUserID(context),String.valueOf(1),new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    Log.i("排行榜数据",apiResponse.getData().toString());
                    list.clear();
                    List<PHBeans> lists = JSONArray.parseArray(apiResponse.getData().toString(), PHBeans.class);
                    Log.i("排行榜数据",lists.toString());
                    list.addAll(lists);
                    adapter.notifyDataSetChanged();
                    Log.i("排行榜数据",list.toString());
                    result=true;
                }else {
                    result=false;
                    Log.i("排行榜数据","失败");
                }
            }
        });
        return result;
    }
    @Override
    public void refresh() {
        super.refresh();
    }
    public void setAdapter(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        list = new ArrayList<>();
        manager=new LinearLayoutManager(context);
        if(adapter ==null){
            adapter = new PHBadapters(context,list);
            this.recyclerView.setLayoutManager(manager);
            this.recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }
}
