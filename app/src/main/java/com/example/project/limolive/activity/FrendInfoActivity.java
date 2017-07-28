package com.example.project.limolive.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.FrendUserInfo;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.tencentim.ui.ChatActivity;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomProgressDialog;
import com.tencent.TIMConversationType;


/**
 * 作者：hpg on 2016/12/27 15:45
 * 功能：好友个人资料
 */
public class FrendInfoActivity extends BaseActivity {
    private ImageView iv_hostHead;
    private TextView host_name, tv_host_id, live_state, tv_like_Num, tv_guanzhu_Num, tv_Location, tv_sendMsg;
    private FriendInfoBean friendInfoBeen;
    private FrendUserInfo frendUserInfo;
    protected CustomProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frendinfo);
        friendInfoBeen = (FriendInfoBean) getIntent().getExtras().getSerializable("friendInfoBeen");
        Log.i("用户详情", friendInfoBeen.toString());
        initView();
        bindEvent();
    }

    private void initView() {
        if (mProgressDialog != null) {
            mProgressDialog = null;
            System.gc();
        }
        mProgressDialog = new CustomProgressDialog(this);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        loadTitle();
        iv_hostHead = (ImageView) findViewById(R.id.iv_hostHead);
        host_name = (TextView) findViewById(R.id.host_name);
        tv_host_id = (TextView) findViewById(R.id.tv_host_id);
        live_state = (TextView) findViewById(R.id.live_state);
        tv_like_Num = (TextView) findViewById(R.id.tv_like_Num);
        tv_guanzhu_Num = (TextView) findViewById(R.id.tv_guanzhu_Num);
        tv_Location = (TextView) findViewById(R.id.tv_Location);
        tv_sendMsg = (TextView) findViewById(R.id.tv_sendMsg);
        mProgressDialog.setMessage("正在处理，请稍后...");
        mProgressDialog.show();
        getFriendUserInfo();
    }

    private void loadTitle() {
        setTitleString(getString(R.string.friends_info_title));
        setLeftImage(R.mipmap.icon_return);
        setRightText("设置");

        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void bindEvent() {
        tv_sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FrendInfoActivity.this, ChatActivity.class);
                intent.putExtra("identify", frendUserInfo.getPhone());
                intent.putExtra("type", TIMConversationType.C2C);
                intent.putExtra("name", frendUserInfo.getNickname());
                intent.putExtra("headsmall", frendUserInfo.getHeadsmall());

                Bundle bundle = new Bundle();
                bundle.putSerializable("friendInfoBeen", friendInfoBeen);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 用户详情
     */
    private void getFriendUserInfo() {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, Presenter.NET_UNCONNECT);

            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            return;
        } else {
            Api.frendUserInfo(LoginManager.getInstance().getUserID(this), friendInfoBeen.getUid(), new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("用户详情", apiResponse.toString());
                    if (apiResponse.getCode() == Api.SUCCESS) {
                        if (mProgressDialog.isShowing()) {
                            mProgressDialog.dismiss();
                        }
                        frendUserInfo = JSON.parseObject(apiResponse.getData(), FrendUserInfo.class);

                        if (!TextUtils.isEmpty(friendInfoBeen.getHeadsmall())) {
                            if (friendInfoBeen.getHeadsmall().contains("http://")){
                                Glide.with(FrendInfoActivity.this).load(friendInfoBeen.getHeadsmall()).into(iv_hostHead);
                            }else {
                                Glide.with(FrendInfoActivity.this).load(ApiHttpClient.API_PIC + friendInfoBeen.getHeadsmall()).into(iv_hostHead);
                            }
                        } else {
                            iv_hostHead.setImageResource(R.mipmap.head3);
                        }
                        host_name.setText(frendUserInfo.getNickname());
                        tv_host_id.setText(frendUserInfo.getPhone());
                        tv_like_Num.setText(frendUserInfo.getFans());
                        tv_guanzhu_Num.setText(frendUserInfo.getFollows());
                        tv_Location.setText(frendUserInfo.getProvince());
                        String is_live = frendUserInfo.getIs_live();
                        if (is_live.equals("1")) {  //在直播
                            live_state.setText("正在直播");
                            live_state.setTextColor(Color.parseColor("#FF5740"));
                            live_state.setBackground(getResources().getDrawable(R.drawable.bg_liveing_button2));
                        } else if (is_live.equals("0")) { //不在直播
                            live_state.setText("直播结束");
                        }

                        setRightRegionListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(FrendInfoActivity.this, FrendInfoSettingActivity.class);
                                intent.putExtra("friendPone", frendUserInfo.getPhone());
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(String errMessage) {
                    super.onFailure(errMessage);
                    if (mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }
            });
        }
    }
}
