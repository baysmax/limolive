package com.example.project.limolive.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

/**
 * 作者：黄亚菲 on 2017/2/24 09:37
 * 功能：用户反馈
 */
public class UserRebackActivity extends BaseActivity implements View.OnClickListener{

    //反馈内容
    private EditText et_reback;
    //提交按钮
    private TextView tv_commit;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reback);

        initView();
    }


    private void initView(){
        context = this;
        et_reback = (EditText) findViewById(R.id.et_reback);
        tv_commit = (TextView) findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(this);
        loadTitle();
    }



    private void loadTitle() {
        setTitleString(getString(R.string.mine_user_reback),18,R.color.black);
        setLeftImage(R.drawable.fanhuihei);
        setBackgroundColor(0xfff);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_commit:
                //提交按钮
                if (!TextUtils.isEmpty(et_reback.getText().toString())){
                    commitReback(LoginManager.getInstance().getUserID(context),et_reback.getText().toString());
                }else {
                    ToastUtils.showShort(context,"请输入您的反馈意见！");
                }
                break;
        }

    }


    /********
     * 提交用户反馈
     * /appapi/Goodstype/user_retroact
     */
    private void commitReback(String uid,String content){

        if (NetWorkUtil.isNetworkConnected(context)){

            Api.commitReback(uid, content, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    if (apiResponse.getCode() == 0){

                        ToastUtils.showShort(context,"反馈意见提交成功！");
                        finish();

                    }else {
                        ToastUtils.showShort(context,apiResponse.getMessage());
                    }

                }
            });


        }else{
            ToastUtils.showShort(context,"网络异常，请检查您的网络~");
        }

    }


}
