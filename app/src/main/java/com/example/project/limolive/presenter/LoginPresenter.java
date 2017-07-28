package com.example.project.limolive.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.LoginActivity;
import com.example.project.limolive.activity.MainActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.ActivityHelper;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.model.LoginModel;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.provider.MineDataProvider;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.presenters.viewinface.LoginView;
import com.example.project.limolive.tencentlive.presenters.viewinface.LogoutView;
import com.example.project.limolive.tencentlive.utils.LogConstants;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.example.project.limolive.utils.CountDownTimerUtils;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.welcome.WelcomeActivity;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import static com.example.project.limolive.tencentlive.utils.Constants.SDK_APPID;

/**
 * 登录
 * <p>本方法包含关于登录的各个方法，退出登录见{@link } loginOut方法</p>
 *
 * @author hwj on 2016/12/13.
 */

public class LoginPresenter extends Presenter {

    //登录页面请求
    public static final int AUTO_LOGIN_REQUEST = 300;
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILED = 2;
    private LoginView mLoginView;
    private LogoutView mLogoutView;

    private ProgressDialog loginDialog;
    private String localJson;

    private static final String TAG = "LoginPresenter";

    private Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    afterLoginSuccess();
                    break;
                case LOGIN_FAILED:
                    if (loginDialog != null && loginDialog.isShowing()) {
                        loginDialog.dismiss();
                    }
                    break;
            }
        }
    };


    public LoginPresenter(Context context) {
        super(context);
    }

    public LoginPresenter(Context context, LoginView mLoginView) {
        super(context);
        this.mLoginView = mLoginView;
    }

    public LoginPresenter(Context context, LogoutView mLogoutView) {
        super(context);
        this.mLogoutView = mLogoutView;
    }

    /**
     * 注册
     *
     * @param username 用户名
     * @param pwd      密码
     * @param pwd_ok   确认密码
     * @param phone    电话
     * @param code     验证码
     */
    public void register(String username, final String pwd
            , String pwd_ok, final String phone, String code) {
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort(context, getString(R.string.login_error2));
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort(context, getString(R.string.login_error4));
            return;
        } else if (TextUtils.isEmpty(pwd_ok)) {
            ToastUtils.showShort(context, getString(R.string.login_error3));
            return;
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(context, getString(R.string.login_error1));
            return;
        } else if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort(context, getString(R.string.login_error5));
            return;
        } else if (!pwd.equals(pwd_ok)) {
            ToastUtils.showShort(context, getString(R.string.login_error6));
            return;
        } else if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(REGISTERING);
        dialog.show();
        Api.register(phone, pwd, username, code, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("注册接口", apiResponse.toString());
                //TODO 注册成功
                dialog.dismiss();
                if (apiResponse.getCode() == Api.SUCCESS) {
                   /* Intent intent = new Intent();
                    intent.putExtra("phone", phone);
                    intent.putExtra("pwd", pwd);
                    ((Activity) context).setResult(Activity.RESULT_OK, intent);*/
                    ((Activity) context).finish();
                } else {
                    ToastUtils.showShort(context, apiResponse.getMessage());
                }
            }
        });
    }

    /**
     * 获取用户sig
     *
     * @param phone
     */
    public void getUserSig(final String phone) {
        Api.getUserSig(phone, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                //TODO 获取sig成功
                Log.i("获取sig", phone);
                Log.i("获取sig", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    //注册成功之后 获取sig并保存本地
                    Log.i("获取sig", apiResponse.toString());
                    JSONObject jsonObject = JSON.parseObject(apiResponse.getData());
                    String sig = jsonObject.getString("sig");
                    LoginManager.getInstance().setSig(context, sig);
                    LiveMySelfInfo.getInstance().setUserSig(sig);
                    LiveMySelfInfo.getInstance().setPhone(phone);

                    //   imLogin(phone, sig);
                    imLogin(phone, sig);
                    Log.i("IM登录", "LoginManager.getInstance().getPhone(context)..:" +
                            LoginManager.getInstance().getPhone(context) + "..LoginManager.getInstance().getSig(context)" +
                            LoginManager.getInstance().getSig(context) + "..LoginManager.getInstance().getId(context)" +
                            LoginManager.getInstance().getUserID(context));
                } else {
                    Log.i("获取sig", "失败");
                    loginHandler.sendEmptyMessage(LOGIN_FAILED);
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                Log.i("获取sig", "errMessage..." + errMessage);
            }
        });
    }

    /**
     * 登录直播iLivesdk
     *
     * @param phone   用户id
     * @param userSig 用户签名
     */
    public void imLogin(final String phone, String userSig) {

        //TODO 新方式登录ILiveSDK
        ILiveLoginManager.getInstance().iLiveLogin(phone, userSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                SxbLog.d(TAG, LogConstants.ACTION_HOST_CREATE_ROOM + LogConstants.DIV + phone + LogConstants.DIV + "request room id");
                Log.i("imsdk回调", "imsdk成功回调" + data.toString());
                getMyRoomNum(phone);
                if (mLoginView != null)
                    mLoginView.loginSucc();
                imSDKLogin();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.d(TAG, LogConstants.ACTION_HOST_CREATE_ROOM + LogConstants.DIV + "tilvblogin failed:" + module + "|" + errCode + "|" + errMsg);
                Log.i("imsdk回调", "imsdk失败回调" + errMsg.toString());
                Log.i("imsdk回调", "imsdk失败回调" + errCode);
                if (mLoginView != null)
                    mLoginView.loginFail();
                loginHandler.sendEmptyMessage(LOGIN_FAILED);
                if (errCode == 70001) {
                    ToastUtils.showShort(context, "sig过期，请从新登录");
                    //跳转到登录页面
                    Intent intent = new Intent();
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                    ((WelcomeActivity) context).finish();
                }
            }
        });
    }

    /**
     * 退出imsdk
     * <p>
     * 退出成功会调用退出AVSDK
     */
    public void imLogout() {
        //TODO 新方式登出ILiveSDK
        ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                SxbLog.i(TAG, "IMLogout succ !");
                //清除本地缓存
                LiveMySelfInfo.getInstance().clearCache(context);
                mLogoutView.logoutSucc();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                SxbLog.e(TAG, "IMLogout fail ：" + module + "|" + errCode + " msg " + errMsg);
            }
        });
    }

    /**
     * 向用户服务器获取自己房间号
     */
    private void getMyRoomNum(final String phone) {
        Api.getMyRoomId(phone, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("获取自己房间号", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    JSONObject jsonObject = JSON.parseObject(apiResponse.getData());
                    String num = jsonObject.getString("num");
                    LiveMySelfInfo.getInstance().setMyRoomNum(num);
                    LoginManager.getInstance().setRoomNum(context, num);
                    //11111
                    LiveMySelfInfo.getInstance().writeToCache(context.getApplicationContext());
                    Log.i("登录成功数据", "当前的user信息" + LiveMySelfInfo.getInstance().toString());
                    Log.i(" 登录成功数据", "获取自己房间号" + num.toString());
                } else {
                    ToastUtils.showShort(context, apiResponse.getMessage());
                }
            }
        });
    }

    /**
     * 获取验证码
     *
     * @param v
     */
    public void getCode(View v, String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(context, getString(R.string.login_error1));
            return;
        }
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, Presenter.NET_UNCONNECT);
            return;
        }
        CountDownTimerUtils timer = new CountDownTimerUtils((TextView) v, 60000, 1000);
        timer.start();
        //TODO 短信验证码
       /* Api.getMsgCode(phone, new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                //"校验码错误吗？"
                ToastUtils.showShort(baseInstance, apiResponse.getMessage());
            }
        });*/
    }

    /**
     * 登录
     *
     * @param phone 电话
     * @param pwd   密码
     */
    public void login(final String phone, String pwd) {
        Log.i("传过来的手机号", phone);
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(context, getString(R.string.login_error1));
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort(context, getString(R.string.login_error4));
            return;
        } else if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        loginDialog = new ProgressDialog(context);
        loginDialog.setMessage(LOGINING);
        loginDialog.show();
        Api.login(phone, pwd, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(final ApiResponse apiResponse) {
                Log.i("登录成功数据", "" + apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    //登录成功
                    localJson = apiResponse.getData();
                    Log.i("登录成功数据", localJson);
                    LiveMySelfInfo.getInstance().setPhone(phone);
                    JSONObject jsonObject = JSON.parseObject(localJson);
                    LiveMySelfInfo.getInstance().setId(jsonObject.getString("uid"));
                    LoginManager.getInstance().setUserID(context, jsonObject.getString("uid"));
                    LiveMySelfInfo.getInstance().setNickName(jsonObject.getString("nickname"));
                    LiveMySelfInfo.getInstance().setAvatar(jsonObject.getString("avatar"));
                    cashData(localJson);
                    getUserSig(phone);

                } else {
                    loginHandler.sendEmptyMessage(LOGIN_FAILED);
                    ToastUtils.showShort(context, apiResponse.getMessage());
                    Log.i("登录成功数据", "" + apiResponse.getMessage().toString());
                }
            }
        });
    }

    private void afterLoginSuccess() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cashData(localJson);
                loginHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //   loginDialog.dismiss();
                        Intent intent = new Intent(context, MainActivity.class); //登录成功跳到首页
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        if (loginDialog != null && loginDialog.isShowing()) {
                            loginDialog.dismiss();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 离线缓存
     *
     * @param json
     */
    public void cashData(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        LoginModel model = JSON.parseObject(json, LoginModel.class);
        MineDataProvider provider = new MineDataProvider(context);
        provider.addMineData(model);//添加缓存

        LoginManager.getInstance().login(context);//登录标记,并缓存用户的UID和PHONE
        LoginManager.getInstance().setUserID(context, model.getUid());
        LoginManager.getInstance().setPhone(context, model.getPhone());
        LoginManager.getInstance().setAvatar(context, model.getHeadsmall());
        LoginManager.getInstance().setHostName(context, model.getNickname());

    }

    @Override
    public void clearTask() {
        super.clearTask();
        ActivityHelper.getInstance().clearHandlerMessage(loginHandler);
    }

    /**
     * 获取验证码（忘记密码）
     *
     * @param v
     * @param phone
     */
    public void getForgetCode(View v, String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(context, getString(R.string.login_error1));
            return;
        }
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, Presenter.NET_UNCONNECT);
            return;
        }
        CountDownTimerUtils timer = new CountDownTimerUtils((TextView) v, 60000, 1000);
        timer.start();
        //TODO 短信验证码
    }

    /**
     * 找回密码
     *
     * @param phone
     * @param pwd
     * @param code
     */
    public void findPassword(String phone, String pwd, String code) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShort(context, getString(R.string.login_error1));
            return;
        } else if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showShort(context, getString(R.string.login_error4));
            return;
        } else if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort(context, getString(R.string.login_error5));
            return;
        } else if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, Presenter.NET_UNCONNECT);
            return;
        }
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(CHANGING);
        dialog.show();
        Api.findPassword(phone, pwd, code, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("找回密码", apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    ToastUtils.showShort(context, apiResponse.getMessage());
                    ((Activity) context).finish();
                } else {
                    ToastUtils.showShort(context, apiResponse.getMessage());
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 腾讯云通讯登录 imsdk
     */
    public void imSDKLogin() {
        // identifier为用户名，userSig 为用户登录凭证
        TIMUser user = new TIMUser();
        user.setIdentifier(LiveMySelfInfo.getInstance().getPhone());

        //发起登录请求
        TIMManager.getInstance().login(
                SDK_APPID,                   //sdkAppId，由腾讯分配
                user,
                LoginManager.getInstance().getSig(context), //用户帐号签名，由私钥加密获得，具体请参考文档
                new TIMCallBack() {//回调接口
                    @Override
                    public void onSuccess() {//登录成功
                        loginHandler.sendEmptyMessage(LOGIN_SUCCESS);
                        Log.d("im云通讯登录", "login succ");
                    }

                    @Override
                    public void onError(int code, String desc) {//登录失败
                        //错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code含义请参见错误码表
                        loginHandler.sendEmptyMessage(LOGIN_FAILED);
                        Log.d("im云通讯登录", "login failed. code: " + code + " errmsg: " + desc);
                        ToastUtils.showShort(context, "云通讯sdk登录失败，请从新登录");
                    }
                });
    }
}
