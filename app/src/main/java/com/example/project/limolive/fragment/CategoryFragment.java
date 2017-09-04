package com.example.project.limolive.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.SearchGoodsActivity;
import com.example.project.limolive.adapter.GoodsAdapter;
import com.example.project.limolive.adapter.GoodsManageAdapter;
import com.example.project.limolive.adapter.Goods_Type_Adapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.BtnBean;
import com.example.project.limolive.bean.GoodsTypeBeen;
import com.example.project.limolive.bean.home.LunBoPicBean;
import com.example.project.limolive.bean.taowu.RecommendBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.ImageCycleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AAA on 2017/8/21.
 */

public class CategoryFragment extends BaseFragment {
    private ImageCycleView home_banner;
    private SwipeRefreshLayout srl_down_category;
    private RecyclerView rv_btn,rv_fm;
    private LinearLayoutManager lm;
    private LinearLayoutManager lm1;
    private List<BtnBean> typeLsit;
    private Goods_Type_Adapter adapter;
    private TextView tv_isgone;
    private RelativeLayout rl_search;


    private List<RecommendBean> goodsLsit;
    private GoodsAdapter adapter1;

    private int page=1;
    private String id="";
    private SwipeRefreshLayout swl_fm;//分类刷新

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_category,inflater,container);
    }
    @Override
    protected void initView() {
        super.initView();
        typeLsit=new ArrayList<>();
        goodsLsit=new ArrayList<>();
        srl_down_category= (SwipeRefreshLayout) findViewById(R.id.srl_down_category);
        home_banner = (ImageCycleView) findViewById(R.id.home_banners);
        tv_isgone= (TextView) findViewById(R.id.tv_isgone);
        rl_search= (RelativeLayout) findViewById(R.id.rl_search);
        initRecyclerView();
        initData();
        Carousel();
        setListener();
    }
    private void initData() {
        get_hotGoodstype();
    }
    public void get_hotGoodstype() {
        Api.get_hotGoodstype(new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("热门商品类型", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    typeLsit.clear();
                    typeLsit.addAll(JSON.parseArray(apiResponse.getData(), BtnBean.class));
                    adapter.notifyDataSetChanged();
                    srl_down_category.setRefreshing(false);
                    Log.i("热门商品类型","goodsLsit1="+typeLsit.size());
                    initDatas(typeLsit.get(0).getType_id(),page);
                } else {
                    goodsType();
                }

            }
            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                srl_down_category.setRefreshing(false);
            }
        });
    }
    public void goodsType() {
        Api.get_goodstype(new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("商品类型", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    typeLsit.clear();
                    typeLsit.addAll(JSON.parseArray(apiResponse.getData(), BtnBean.class));
                    adapter.notifyDataSetChanged();
                    Log.i("热门商品类型","goodsLsit="+typeLsit.size());
                    srl_down_category.setRefreshing(false);
                    initDatas(typeLsit.get(0).getType_id(),page);
                } else {
                    goodsType();
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                srl_down_category.setRefreshing(false);
            }
        });
    }

    private void initRecyclerView() {
        lm=new LinearLayoutManager(getActivity());
        lm1=new LinearLayoutManager(getActivity());
        rv_btn= (RecyclerView) findViewById(R.id.rv_btn);
        rv_fm= (RecyclerView) findViewById(R.id.rv_fm);
        swl_fm= (SwipeRefreshLayout) findViewById(R.id.swl_fm);

        rv_btn.setLayoutManager(lm);
        rv_fm.setLayoutManager(lm1);
        adapter=new Goods_Type_Adapter(typeLsit,getActivity());
        rv_btn.setAdapter(adapter);
        adapter1=new GoodsAdapter(goodsLsit,getActivity());
        rv_fm.setAdapter(adapter1);
    }

    private void setListener() {
        swl_fm.setRefreshing(true);
        swl_fm.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swl_fm.setRefreshing(true);
                initDatas(id,page);
            }
        });
        rv_fm.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int positon = lm1.findLastVisibleItemPosition();
                if (adapter!=null&&positon==adapter.getItemCount()-2/*&&newState==RecyclerView.SCROLL_STATE_IDLE*/){
                    page++;
                    initDatas(id,page);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(),SearchGoodsActivity.class);
                startActivity(intent);
            }
        });
        srl_down_category.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Carousel();
                initData();
            }
        });
        adapter.setOnItemClickListener(new Goods_Type_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //更换显示数据
                BtnBean btnBean = typeLsit.get(position);
                id = btnBean.getType_id();
                Log.i("热门商品类型","btnBean="+btnBean.toString());
                page=1;
                initDatas(id,page);
            }


        });
    }
    private void initDatas(String id, final int page) {
        Api.getGoodsList(LoginManager.getInstance().getUserID(getActivity()), id,String.valueOf(page), new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode()==Api.SUCCESS){
                    tv_isgone.setVisibility(View.GONE);
                    rv_fm.setVisibility(View.VISIBLE);
                    if (page==1){
                        goodsLsit.clear();
                    }

                    adapter1.notifyDataSetChanged();
                    List<RecommendBean> list = JSONArray.parseArray(apiResponse.getData(), RecommendBean.class);
                    if (list!=null&&list.size()>0){
                        goodsLsit.addAll(list);
                        adapter1.notifyDataSetChanged();
                    }else {
                        ToastUtils.showShort(getActivity(),"没有更多了");
                    }
                    Log.i("热门商品类型","goodsLsit="+goodsLsit.size());
                    if (swl_fm!=null&&swl_fm.isRefreshing()){
                        swl_fm.setRefreshing(false);
                    }
                }else if(apiResponse.getCode()==-2) {
                    if(goodsLsit!=null&&goodsLsit.size()==0){
                        tv_isgone.setVisibility(View.VISIBLE);
                        rv_fm.setVisibility(View.GONE);
                    }
                    if (swl_fm!=null&&swl_fm.isRefreshing()){
                        swl_fm.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                Log.i("热门商品类型","失败");
                if (swl_fm!=null&&swl_fm.isRefreshing()){
                    swl_fm.setRefreshing(false);
                }
            }
        });
    }
    public void Carousel() {
        final List<LunBoPicBean> lunBoPicBeen = new ArrayList<LunBoPicBean>();
        final List<String> string = new ArrayList<String>();
        Api.carousel(new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("轮播图", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    lunBoPicBeen.addAll(JSON.parseArray(apiResponse.getData(), LunBoPicBean.class));
                    string.clear();
                    for (LunBoPicBean l : lunBoPicBeen) {
                        string.add(l.getImg());
                    }
                    if (string != null && string.size() > 0)
                        home_banner.setImageResources(string);
                } else {
                    ToastUtils.showShort(getActivity(), apiResponse.getMessage());
                }
            }
        });
    }

}
