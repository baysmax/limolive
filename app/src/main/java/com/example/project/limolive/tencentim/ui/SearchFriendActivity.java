package com.example.project.limolive.tencentim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.FrendInfoActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.adapters.searchNewFriendAdapter;
import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.tencentim.model.ProfileSummary;
import com.example.project.limolive.tencentim.model.searchFriendBean;
import com.example.project.limolive.tencentlive.adapters.FriendListAdapter;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 查找添加新朋友
 */
public class SearchFriendActivity extends Activity implements View.OnKeyListener {

    private List<searchFriendBean> searchFriendBeens;
    private List<searchFriendBean> searchIsFriendBeens;
    private ListView mSearchList;
    private EditText mSearchInput;
    private TextView tvNoResult;
    private TextView tv_textNull;
    private searchNewFriendAdapter searchAdapter;
    private FriendListAdapter IsFriendAdapter;
    private List<ProfileSummary> list = new ArrayList<>();
    private String fromWhere;
    private FriendInfoBean friendInfoBeen = new FriendInfoBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        fromWhere = getIntent().getStringExtra("fromWhere");
        mSearchInput = (EditText) findViewById(R.id.inputSearch);
        mSearchList = (ListView) findViewById(R.id.list);
        tvNoResult = (TextView) findViewById(R.id.noResult);
        tv_textNull = (TextView) findViewById(R.id.tv_textNull);

        if (TextUtils.equals(fromWhere,"AddFriend")){
            searchFriendBeens = new ArrayList<>();
            searchAdapter = new searchNewFriendAdapter(this, searchFriendBeens);
            mSearchList.setAdapter(searchAdapter);
        }else if (TextUtils.equals(fromWhere,"IsFriend")){
            searchIsFriendBeens = new ArrayList<>();
            IsFriendAdapter = new FriendListAdapter(this, searchIsFriendBeens);
            mSearchList.setAdapter(IsFriendAdapter);

            mSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    friendInfoBeen.setPhone(searchIsFriendBeens.get(position).getPhone());
                    friendInfoBeen.setNickname(searchIsFriendBeens.get(position).getNickname());
                    friendInfoBeen.setUid(searchIsFriendBeens.get(position).getUid());
                    friendInfoBeen.setHeadsmall(searchIsFriendBeens.get(position).getHeadsmall());
                    bundle.putSerializable("friendInfoBeen", friendInfoBeen);
                    intent.putExtras(bundle);
                    intent.setClass(SearchFriendActivity.this, FrendInfoActivity.class);
                    startActivity(intent);
                }
            });
        }
        TextView tvCancel = (TextView) findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSearchInput.setOnKeyListener(this);

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {   // 忽略其它事件
            return false;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                list.clear();
                if (TextUtils.equals(fromWhere,"AddFriend")) {
                    searchAdapter.notifyDataSetChanged();
                }else if (TextUtils.equals(fromWhere,"IsFriend")){
                    IsFriendAdapter.notifyDataSetChanged();
                }
                String key = mSearchInput.getText().toString();
                Log.i("搜索好友", "key。。" + key);
                if (key.equals("")) {
                    return true;
                }

                if (TextUtils.equals(fromWhere,"AddFriend")) {
                    searchfriend(key);
                }else if (TextUtils.equals(fromWhere,"IsFriend")){
                    searchIsFriendname(key);
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * 搜索加好友
     */
    private void searchfriend(String name) {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.searchfriend(LoginManager.getInstance().getUserID(this), name, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("搜索好友", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    List<searchFriendBean> list = JSON.parseArray(apiResponse.getData(), searchFriendBean.class);
                    searchFriendBeens.clear();
                    searchFriendBeens.addAll(list);
                } else {
                    tv_textNull.setText(apiResponse.getMessage());
                }
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
            }
        });
    }

    /**
     * 搜索好友店铺中的好友
     */
    private void searchIsFriendname(String name) {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.searchFriendname(LoginManager.getInstance().getUserID(this), name, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("搜索好友", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    List<searchFriendBean> list = JSON.parseArray(apiResponse.getData(), searchFriendBean.class);
                    searchIsFriendBeens.clear();
                    searchIsFriendBeens.addAll(list);
                } else {
                    tv_textNull.setText(apiResponse.getMessage());
                }
                IsFriendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
            }
        });
    }
}
