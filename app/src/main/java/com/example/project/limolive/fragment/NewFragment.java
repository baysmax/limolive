package com.example.project.limolive.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.NewAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.FollowLiveBeans;
import com.example.project.limolive.bean.NewLiveBean;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.helper.LoginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/14.
 */

public class NewFragment extends BaseFragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<HomeListBeen> newLiveList;
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
//        for (int i=0;i<10;i++){
//            newLiveList.add(new NewLiveBean("","1212","123",true,"背景"));
//        }
        initDate();
        adapter=new NewAdapter(newLiveList,getActivity());
        recyclerView.setAdapter(adapter);
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen._9sp);
//        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
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
        swipeRefreshLayout.setRefreshing(true);
        Api.newsDate(LoginManager.getInstance().getUserID(getActivity()),page, new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    String data = apiResponse.getData();
                    Log.d("直播列表", "data: "+data.toString());
                    newLiveList.addAll(JSONArray.parseArray(data, HomeListBeen.class));
                    adapter.notifyDataSetChanged();
                    Log.d("直播列表", "newLiveList: "+newLiveList.get(0).toString());

                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }


}
