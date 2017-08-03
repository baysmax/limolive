package com.example.project.limolive.presenter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.adapter.OrderAdapter;
import com.example.project.limolive.adapter.PHBadapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.order.OrderBean;
import com.example.project.limolive.bean.phb.PHBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/2.
 */

public class PhbPersenter extends Presenter{
    private Context context;
    private List<PHBean> list;
    private RecyclerView recyclerView;
    private PHBadapter adapter;
    private LinearLayoutManager manager;

    public PhbPersenter(Context context) {
        super(context);
        this.context=context;
    }

    public void getDate(String uid,String hostid){
        Api.getRanking(uid, hostid, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    Log.i("排行榜数据",apiResponse.getData().toString());
                    list.clear();
                    List<PHBean> lists = JSONArray.parseArray(apiResponse.getData().toString(), PHBean.class);
                    Log.i("排行榜数据",lists.toString());
                    list.addAll(lists);
                    adapter.notifyDataSetChanged();
                    Log.i("排行榜数据",list.toString());
                }else {
                    Log.i("排行榜数据","失败");
                }
            }
        });
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
            adapter = new PHBadapter(context,list);
            this.recyclerView.setLayoutManager(manager);
            this.recyclerView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }
}
