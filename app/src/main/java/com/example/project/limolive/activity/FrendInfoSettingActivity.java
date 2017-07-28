package com.example.project.limolive.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.ToastUtils;


/**
 * 作者：hpg on 2016/12/27 15:45
 * 功能：好友个人资料
 */
public class FrendInfoSettingActivity extends BaseActivity implements View.OnClickListener{
    private TextView tv_detFrend;
    private ImageView iv_open_closs;
    private String friendPone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frendsetting);
        friendPone = getIntent().getStringExtra("friendPone");
        initView();
        bindEvent();
    }

    private void initView() {
        loadTitle();
        tv_detFrend = (TextView) findViewById(R.id.tv_detFrend);
        iv_open_closs = (ImageView) findViewById(R.id.iv_open_closs);

    }

    private void loadTitle() {
        setTitleString(getString(R.string.friends_info_title));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void bindEvent() {
        iv_open_closs.setOnClickListener(this);
        tv_detFrend.setOnClickListener(this);
    }

    /**
     * 删除好友
     */
    private void deleteFriend() {
        Api.deleteFriend(LoginManager.getInstance().getUserID(this), friendPone, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("删除好友", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    ToastUtils.showShort(FrendInfoSettingActivity.this,apiResponse.getMessage());
                }else {
                    ToastUtils.showShort(FrendInfoSettingActivity.this,apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
            }
        });
    }

    /**
     * 拉黑取消拉黑
     */
    private void pullBlack() {
        Api.pullBlack(LoginManager.getInstance().getUserID(this), friendPone, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("拉黑取消拉黑", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    ToastUtils.showShort(FrendInfoSettingActivity.this,apiResponse.getMessage());
                }else {
                    ToastUtils.showShort(FrendInfoSettingActivity.this,apiResponse.getMessage());
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_detFrend://删除好友
                deleteFriend();
                break;
            case R.id.iv_open_closs://拉黑与取消拉黑
                if (iv_open_closs.isSelected()){
                    iv_open_closs.setSelected(false);
                    //取消拉黑
                    pullBlack();
                }else {
                    iv_open_closs.setSelected(true);
                    //拉黑
                    pullBlack();
                }
                break;
        }
    }
}
