package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
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

    private List<RecommendBean> goodsLsit;
    private GoodsAdapter adapter1;

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

        rv_btn.setLayoutManager(lm);
        rv_fm.setLayoutManager(lm1);
        adapter=new Goods_Type_Adapter(typeLsit,getActivity());
        rv_btn.setAdapter(adapter);
        adapter1=new GoodsAdapter(goodsLsit,getActivity());
        rv_fm.setAdapter(adapter1);
    }

    private void setListener() {
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
                String id = btnBean.getId();
                Api.getGoodsList(LoginManager.getInstance().getUserID(getActivity()), id, new ApiResponseHandler(getActivity()) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode()==Api.SUCCESS){
                            goodsLsit.clear();
                            goodsLsit.addAll(JSONArray.parseArray(apiResponse.getData(),RecommendBean.class));

                            adapter1.notifyDataSetChanged();
                        }
                    }
                });
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
