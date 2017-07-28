package com.example.project.limolive.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.MoreLiveingAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.live.Be_Liveing_Bean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.ui.SearchFriendActivity;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：hpg on 2016/12/29 11:28
 * 功能：
 */
public class MoreLiveingActicity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private GridView gv_morelive;
    private List<Be_Liveing_Bean> be_Liveing_Beans;//在直播的2个（更多）
    private String type = "type";
    private MoreLiveingAdapter adapter;
    private AutoSwipeRefreshLayout swipe_refresh_tool;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreliveing);
        getLiveLists(type, page);
        initView();
    }

    private void initView() {
        loadTitle();
        gv_morelive = (GridView) findViewById(R.id.gv_morelive);
        swipe_refresh_tool = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_refresh_tool);
        swipe_refresh_tool.setOnRefreshListener(this);
        swipe_refresh_tool.setColorSchemeColors(Color.parseColor("#31D5BA"));
        be_Liveing_Beans = new ArrayList<>();
        adapter = new MoreLiveingAdapter(this, be_Liveing_Beans);
        gv_morelive.setAdapter(adapter);
        swipe_refresh_tool.autoRefresh();
        BindEvent();
    }

    private void BindEvent() {

    }

    private void loadTitle() {
        setTitleString(getString(R.string.friends_store_liveing));
        setRightImage(R.mipmap.add_friends);
        setRightRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoreLiveingActicity.this, SearchFriendActivity.class);
                intent.putExtra("fromWhere","AddFriend");
                MoreLiveingActicity.this.startActivity(intent);
            }
        });
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 在直播的2个（更多）
     */
    private void getLiveLists(String type, int page) {
        Api.livelists(LoginManager.getInstance().getUserID(this), type, page, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("在直播的更多", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    List<Be_Liveing_Bean> list = JSON.parseArray(apiResponse.getData(), Be_Liveing_Bean.class);
                    be_Liveing_Beans.clear();
                    be_Liveing_Beans.addAll(list);
                }
                swipe_refresh_tool.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                ToastUtils.showShort(MoreLiveingActicity.this, errMessage.toString());
                swipe_refresh_tool.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        page++;
        getLiveLists(type, page);
    }
}
