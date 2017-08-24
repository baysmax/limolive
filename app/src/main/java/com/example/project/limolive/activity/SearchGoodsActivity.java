package com.example.project.limolive.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.GoodsAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.bean.taowu.RecommendBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/16.
 */

public class SearchGoodsActivity extends BaseActivity implements View.OnClickListener {
    private TextView  tv_search;
    private RecyclerView rv_search;
    private List<RecommendBean> list_search;
    private GoodsAdapter searchAdapter;
    private EditText inputSearch;
    private RelativeLayout rl_rv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_search);
        initView();
    }



    private void initView() {
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_search.setOnClickListener(this);
        rl_rv_search = (RelativeLayout) findViewById(R.id.rl_rv_search);
        rv_search = (RecyclerView) findViewById(R.id.rv_search);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        rl_rv_search.setVisibility(View.GONE);

        initRecyclerView();
    }

    private void initRecyclerView() {
        list_search = new ArrayList<>();
        searchAdapter=new GoodsAdapter(list_search,this);
        rv_search.setLayoutManager(new LinearLayoutManager(this));
        rv_search.setAdapter(searchAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                String tv_search = inputSearch.getText().toString();

                search(tv_search);
                break;

        }

    }

    private void search(String tv_search) {
        Log.i("搜索","tv_search="+tv_search);
        list_search.clear();
        if (TextUtils.isEmpty(tv_search)){
            ToastUtils.showShort(this,"请输入内容");
            return;
        }
        rl_rv_search.setVisibility(View.VISIBLE);

        Api.searchs(tv_search, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {

                Log.i("搜索","apiResponse="+apiResponse.getData());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    list_search.clear();
                    list_search.addAll(JSONArray.parseArray(apiResponse.getData(), RecommendBean.class));
                    Log.i("搜索","list_search="+list_search.size());
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
