package com.example.project.limolive.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.GoodsManageAdapter;
import com.example.project.limolive.adapter.GoodsManagesAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.GoodInfoBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.R.id.swipe_refresh_tool;
import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 作者：hpg on 2017/1/6 13:11
 * 功能：
 */
public class GoodsManagsActivity extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {
    private ListView lv_goodsMGList;
    private LinearLayout ll_sell, ll_selled;
    private View v_selledBG, v_sellBG;
    private List<GoodInfoBean> goodsContentBeans;
    private TextView tv_textNull;
    private GoodsManagesAdapter adapter;

    private AutoSwipeRefreshLayout swipeRefreshLayout;

    private String uid;
    //1为在售商品 2为已售
    private String sort;
    private int page = 0;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_goods_manage);
        initView();

        /*if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
            ToastUtils.showShort(mContext, "网络问题，请您稍后重试！");
            ApiHttpClient.cancelAllRequest();
        }*/
    }

    private void initView() {
        loadTitle();
        mContext = this;
        uid = getIntent().getStringExtra("uid");
        swipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(swipe_refresh_tool);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#31D5BA"));
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goodsManager(uid,sort,String.valueOf(page));
            }
        });
        lv_goodsMGList = (ListView) findViewById(R.id.lv_goodsMGList);
        ll_sell = (LinearLayout) findViewById(R.id.ll_sell);
        ll_selled = (LinearLayout) findViewById(R.id.ll_selled);
        v_selledBG = (View) findViewById(R.id.v_selledBG);
        v_sellBG = (View) findViewById(R.id.v_sellBG);
        tv_textNull = (TextView) findViewById(R.id.tv_textNull);


        //第一次进入 显示出售数据
        v_sellBG.setVisibility(View.VISIBLE);
        v_selledBG.setVisibility(View.GONE);
        sort = "1";
        goodsManager(uid,sort,String.valueOf(page));

        goodsContentBeans = new ArrayList<>();
        adapter = new GoodsManagesAdapter(this, goodsContentBeans);
        lv_goodsMGList.setAdapter(adapter);
        bindEvent();
    }

    private void bindEvent() {
        ll_sell.setOnClickListener(this);
        ll_selled.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_sell:
                //出售  加载出售的数据
                sort = "1";
                v_sellBG.setVisibility(View.VISIBLE);
                v_selledBG.setVisibility(View.GONE);
                goodsManager(uid,sort,String.valueOf(page));
                break;
            case R.id.ll_selled:
                //已出售 加载已出售数据
                sort = "2";
                v_selledBG.setVisibility(View.VISIBLE);
                v_sellBG.setVisibility(View.GONE);
                goodsManager(uid,sort,String.valueOf(page));
                break;
        }
    }

    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        setTitleString("店家商品");
        setLeftImage(R.mipmap.icon_return);
//        setRightImage(R.mipmap.fenlei);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 商品管理
     */
    private void goodsManager(String uid,String type,String page) {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        } else {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage(Presenter.LOADING);
            dialog.show();
            Api.goodsManager(uid, type, page, new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    if (apiResponse.getCode() == Api.SUCCESS) {
                        Log.i("商品管理","网络获取"+apiResponse.toString());
                        List<GoodInfoBean> list = JSON.parseArray(apiResponse.getData(), GoodInfoBean.class);
                        goodsContentBeans.clear();
                        goodsContentBeans.addAll(list);
                     //   adapter.notifyDataSetChanged();
                    } else if (apiResponse.getCode() == -2) {
                        tv_textNull.setVisibility(View.VISIBLE);
                        goodsContentBeans.clear();
                    } else {
                        goodsContentBeans.clear();
                        ToastUtils.showShort(GoodsManagsActivity.this, apiResponse.getMessage().toString());
                    }
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onRefresh() {

    }
}
