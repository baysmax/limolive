package com.example.project.limolive.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.GoodsDetails;
import com.example.project.limolive.adapter.FindGoodsList_Adapter;
import com.example.project.limolive.adapter.HorizontalListAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.taowu.RecommendBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomProgressDialog;
import com.example.project.limolive.view.HorizontalListView;
import com.example.project.limolive.view.MyListview;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 淘物 fragment
 *
 * @author hpg  建立
 *         2016.12.19
 */
public class FindGoodsFragment extends BaseFragment {
    private MyListview lv_all;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    private View firstHeader;
    private HorizontalListView mHorizontalListView;
    private HorizontalListAdapter mAdapter;
    private FindGoodsList_Adapter fAdapter;
    private List<RecommendBean> rb_baokuan;
    private List<RecommendBean> rb_goods;
    private int page=1;
    protected CustomProgressDialog mProgressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_shopping_cart, inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        if (mProgressDialog != null) {
            mProgressDialog = null;
            System.gc();
        }
        mProgressDialog = new CustomProgressDialog(getActivity());
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        loadTitle();
        rb_baokuan = new ArrayList<>();
        rb_goods = new ArrayList<>();
        lv_all = (MyListview) findViewById(R.id.lv_all);
        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        swipe_refresh_tool.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getRecommend();
                getGoods();
            }
        });
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
        firstHeader = ViewGroup.inflate(getActivity(), R.layout.findgoods_secondhead, null);
        mHorizontalListView = (HorizontalListView)firstHeader.findViewById(R.id.hl_baokuan_horizontallist);
        lv_all.addHeaderView(firstHeader);
        mAdapter = new HorizontalListAdapter(getActivity(),rb_baokuan);
        mHorizontalListView.setAdapter(mAdapter);
        fAdapter = new FindGoodsList_Adapter(getActivity(),rb_goods);
        lv_all.setAdapter(fAdapter);
        mProgressDialog.setMessage("正在处理，请稍后...");
        mProgressDialog.show();
        getRecommend();
        getGoods();


        mHorizontalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), GoodsDetails.class).putExtra("goods_id",rb_baokuan.get(position).getGoods_id()));
            }
        });

        lv_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>0)
                startActivity(new Intent(getActivity(), GoodsDetails.class).putExtra("goods_id",rb_goods.get(position-1).getGoods_id()));
            }
        });



    }

    private void loadTitle() {
        setTitleString(getString(R.string.findgoods_fragment_title));
//        setLeftImage(R.mipmap.icon_return);
//        setLeftRegionListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
    }

    private void getRecommend() {
        /**
         * 获取爆款商品
         */
        if (!NetWorkUtil.isNetworkConnected(getActivity())) {
            ToastUtils.showShort(getActivity(), NET_UNCONNECT);
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            return;
        }

        Api.getRecommend(LoginManager.getInstance().getUserID(getActivity()), new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("获取爆款商品","apiResponse..."+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                    rb_baokuan.clear();
                    rb_baokuan.addAll(JSON.parseArray(apiResponse.getData(),RecommendBean.class));
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(getActivity(), apiResponse.getMessage());
                }

            }

            @Override
            public void onFailure(String errMessage) {
                ToastUtils.showShort(getActivity(),errMessage);
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                super.onFailure(errMessage);
            }
        });
    }

    private void getGoods() {
        /**
         * 获取普通商品
         */
        if (!NetWorkUtil.isNetworkConnected(getActivity())) {
            swipe_refresh_tool.setRefreshing(false);
            ToastUtils.showShort(getActivity(), NET_UNCONNECT);
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            return;
        }
        Api.getGoods(LoginManager.getInstance().getUserID(getActivity()),page,new ApiResponseHandler(getActivity()) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("获取普通商品","apiResponse"+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    swipe_refresh_tool.setRefreshing(false);
                    rb_goods.clear();
                    rb_goods.addAll(JSON.parseArray(apiResponse.getData(),RecommendBean.class));
                    fAdapter.notifyDataSetChanged();
                } else {
                    swipe_refresh_tool.setRefreshing(false);
                    ToastUtils.showShort(getActivity(), apiResponse.getMessage());
                }
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(String errMessage) {
                swipe_refresh_tool.setRefreshing(false);
                ToastUtils.showShort(getActivity(),errMessage);
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                super.onFailure(errMessage);
            }
        });
    }
}
