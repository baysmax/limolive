package com.example.project.limolive.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.activity.LoginActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.presenters.viewinface.LoginView;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.welcome.WelcomeActivity;


/**
 * Created by hwj on 2016/8/31.
 * 启动登录管理类
 */
public class WellComeHelper extends Presenter implements LoginView{

    private static final String TAG="WellComeHelper";
    private int startTime=3000;//设置启动动画时间3s
    private LoginManager loginManager;
    private LoginView mLoginView =this;
    private LoginPresenter mLoginPresenter;
    public WellComeHelper(Context context) {
        super(context);
        loginManager = LoginManager.getInstance();
    }

    /**
     * 开启动画
     * @param view
     */
    public void startAlpha(View view) {
        //设置动画效果
        AlphaAnimation animation = new AlphaAnimation(0.5f, 1.0f);
        animation.setDuration(2000);
        if(view!=null){
            view.startAnimation(animation);
        }
    }

    /**
     * 进入app是否登录
     */
    public boolean isLogin() {
        return loginManager.isLogin(context);
    }

    /**
     * 判断是否第一次打开
     */
    public boolean isFirstEnter() {
        return loginManager.isFirstEnter(context);
    }

    /**
     * 是否进入下个页面
     */
    public void intoNext() {
        Thread th= new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(startTime);
            }
        });
        if(isFirstEnter()){
            Log.i(TAG,"第一次登录！");
            //firstEnter();
        }else if(isLogin()){
            Log.i(TAG,"已登录！");
            th.start();
            comeToMain();
        }else{
            Log.i(TAG,"未登录！");
//            comeToLogin();
        }
    }

    /**
     * 跳往登录页面
     */
    private void comeToLogin() {
        Log.i(TAG,"未登录，跳到登录界面！");
        context.startActivity(new Intent(context,LoginActivity.class));
        ((WelcomeActivity)context).finish();
    }

    /**
     * 已经登陆后的操作
     */
    private void comeToMain() {
        /**
         * login-qq
         *      if succusess
         *              enter main
         *      else
         *              login
         *
         * 登录腾讯 成功进入mainactivity    失败 跳入登录页面
         */
        if (!NetWorkUtil.isNetworkConnected(context)){
            context.startActivity(new Intent(context, LoginActivity.class));
            ((WelcomeActivity)context).finish();
            ToastUtils.showShort(context,"网络异常，请检查您的网络连接~");
        }else {
            mLoginPresenter = new LoginPresenter(context,this);
            mLoginPresenter.imLogin(LiveMySelfInfo.getInstance().getPhone(),LiveMySelfInfo.getInstance().getUserSig());
            Log.i("comeToMain","电话"+LiveMySelfInfo.getInstance().getPhone()+"...sig..."+LiveMySelfInfo.getInstance().getUserSig());
        }

    }
    /**
     * 第一次进入app
     */
    private void firstEnter() {
        //TODO 判断是否第一次打开，如果是，则进入图片引导页，否则进行下一步判断
        //TODO 进入图片引导页
        loginManager.firstEnter(context);
        context.startActivity(new Intent(context, LoginActivity.class));
        ((WelcomeActivity)context).finish();
    }


    @Override
    public void loginSucc() {
        Log.i("loginSucc","成功");
        //成功之后 登录云通讯
       // mLoginPresenter.imSDKLogin();
       // context.startActivity(new Intent(context, MainActivity.class));
       // ((WelcomeActivity)context).finish();
    }

    @Override
    public void loginFail() {
        Log.i("腾讯登录","失败");
    }


    /**
     * 向用户服务器获取自己房间号
     */
    private void getMyRoomNum(String phone) {
        Api.getMyRoomId(phone, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("获取自己房间号",apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    JSONObject jsonObject = JSON.parseObject(apiResponse.getData());
                    String num = jsonObject.getString("num");
                    Log.i("获取自己房间号",num.toString());
                    LiveMySelfInfo.getInstance().setMyRoomNum(num);
                    LiveMySelfInfo.getInstance().writeToCache(context.getApplicationContext());
                } else {
                    ToastUtils.showShort(context, apiResponse.getMessage());
                }
            }
        });
    }
}
