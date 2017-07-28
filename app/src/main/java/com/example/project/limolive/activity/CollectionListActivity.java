package com.example.project.limolive.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.MyCollectAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.MyCollectBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏列表
 *
 * @author hwj on 2016/12/19.
 */

public class CollectionListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private int page = 0;

    private List<MyCollectBean> listAll;

    private TextView tv_non_data;

    private AutoSwipeRefreshLayout swipeRefreshLayout;
    private ListView lv_my_collect_list;
    private MyCollectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();
    }

    private void initView() {

        context = this;
        loadTitle();
        listAll = new ArrayList<>();
        tv_non_data = (TextView) findViewById(R.id.tv_non_data);
        tv_non_data.setVisibility(View.INVISIBLE);

        swipeRefreshLayout = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_cart);
        lv_my_collect_list = (ListView) findViewById(R.id.lv_my_collect_list);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(BaseActivity.STATUS_BAR_COLOR_DEFAULT);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                getMyCollectData(LoginManager.getInstance().getUserID(context),page);
            }
        });


        getMyCollectData(LoginManager.getInstance().getUserID(context),0);

    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_collection));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /*****
     * 我的收藏列表
     * /appapi/Goods/getGoodsCollect
     */
    private void getMyCollectData(String uid, int page) {
        if (NetWorkUtil.isNetworkConnected(context)) {

            Api.getMyCollect(uid, page, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    if (apiResponse.getCode() == 0) {

//                        ToastUtils.showShort(context, "请求成功");
                        List<MyCollectBean> list = JSONArray.parseArray(apiResponse.getData(), MyCollectBean.class);

                        if (null != list) {
                            tv_non_data.setVisibility(View.GONE);
                            listAll.clear();
                            listAll.addAll(list);
                            adapter = new MyCollectAdapter(context,listAll);
                            lv_my_collect_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
//                            Log.e("我的收藏数据listAll",listAll.size()+"");
                        }else {
                            tv_non_data.setVisibility(View.VISIBLE);
                        }

                        swipeRefreshLayout.setRefreshing(false);


                    } else {
                        ToastUtils.showShort(context, apiResponse.getMessage());
                        swipeRefreshLayout.setRefreshing(false);
                    }

                }
            });

        } else {
            ToastUtils.showShort(context, "网络异常，请检查您的网络~");
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        if (!swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.autoRefresh();
        }
    }
}
