package com.example.project.limolive.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.ThridLogin;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.LoginPresenter;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.SPUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.welcome.StartActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

import static com.example.project.limolive.activity.BeforeLiveActivity.isShouldHideInput;

/**
 * 登录
 *
 * @author hwj on 2016/12/13.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private LoginPresenter loginPresenter;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    /**
     * 用户名密码
     */
    private EditText edit_user;
    private EditText edit_password;
    private SPUtil sp;
    private Double lon, lat;
    private ImageView iv_wx_login,iv_weibo_login,iv_qq_login;
    private SnsPlatform var1;

    private String denglu="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取个人数据本地缓存
        LiveMySelfInfo.getInstance().getCache(getApplicationContext());

        loginPresenter = new LoginPresenter(this);
        var1 = new SnsPlatform();
        initView();
//        locationAndContactsTask();
    }

    private void initView() {
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        sp = SPUtil.getInstance(this);
        loadTitle();
        edit_user = (EditText) findViewById(R.id.edit_user);
        edit_password = (EditText) findViewById(R.id.edit_password);
        iv_wx_login = (ImageView) findViewById(R.id.iv_wx_login);
        iv_weibo_login = (ImageView) findViewById(R.id.iv_weibo_login);
        iv_qq_login = (ImageView) findViewById(R.id.iv_qq_login);

        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.tv_forgot_pwd).setOnClickListener(this);

        iv_wx_login.setOnClickListener(this);
        iv_weibo_login.setOnClickListener(this);
        iv_qq_login.setOnClickListener(this);
//      BuilderDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mLocationClient.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mLocationClient.stop();
    }

    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        setTitleString(getString(R.string.login_title_text));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
    }

    /**
     * 登录
     *
     * @param v
     */
    public void login(View v) {
        loginPresenter.login(edit_user.getText().toString().trim(),
                edit_password.getText().toString().trim());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register: //前往注册页面
                toRegister();
                break;
            case R.id.tv_forgot_pwd: //前往注册页面
                toForget();
                break;
            case R.id.iv_wx_login: //微信登录
                denglu="2";
                wxLogin();
                break;
            case R.id.iv_weibo_login: //微博登录
                denglu="3";
                weiboLogin();
                break;
            case R.id.iv_qq_login: //qq登录
                denglu="1";
                qqLogin();
                break;
        }
    }

    //qq登录
    private void qqLogin() {
            UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);
    }
    //微博登录
    private void weiboLogin() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.SINA, authListener);
    }
    //微信登录
    private void wxLogin() {
        UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
    }
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            showProgressDialog("请稍后...");
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SocializeUtils.safeCloseDialog(dialog);
            //弹出对话框 让用户填手机号
            Log.i("回调成功返回的数据","data..."+data.get("name"));
            if (platform==SHARE_MEDIA.QQ){
                is_first(data.get("name"),"1",data.get("uid"),data.get("iconurl"));
                Log.i("回调成功返回的数据","QQ..."+data.get("name")+data.get("uid")+data.get("iconurl"));
            }else if (platform==SHARE_MEDIA.WEIXIN){
                is_first(data.get("name"),"2",data.get("uid"),data.get("iconurl"));
                Log.i("回调成功返回的数据","WEIXIN..."+data.get("name")+data.get("uid")+data.get("iconurl"));
            }else if (platform==SHARE_MEDIA.SINA){
                is_first(data.get("name"),"3",data.get("uid"),data.get("iconurl"));
                Log.i("回调成功返回的数据","SINA..."+data.get("name")+data.get("uid")+data.get("iconurl"));
            }
            hideProgressDialog();
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
          //  Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
            hideProgressDialog();
        }
    };

    //是否是第一次登录
    private void is_first(final String nickname, final String type, final String openid, final String headsmall){
        if (NetWorkUtil.isNetworkConnected(this)){
            Log.i("是否是第一次登录","nickname...."+nickname+"type"+type+"openid"+openid+"headsmall"+headsmall);
            Api.is_first(type,openid,new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("是否是第一次登录","apiResponse.getCode()..."+apiResponse.getCode());
                    if (TextUtils.equals(String.valueOf(apiResponse.getCode()),"1")){
                        Log.i("是否是第一次登录","走1 没有");//1...直接登录
                        LoginThrid(nickname,type,openid,headsmall);
                    }else if(TextUtils.equals(String.valueOf(apiResponse.getCode()),"2")){
                        Log.i("是否是第一次登录","走2 没有");
                        Intent intent1 = new Intent(LoginActivity.this,SendPhoneActivity.class);
                        intent1.putExtra("nickname",nickname);
                        intent1.putExtra("type",type);
                        intent1.putExtra("openid",openid);
                        intent1.putExtra("headsmall",headsmall);
                        startActivity(intent1);//2...去绑定手机号
                    }
                }
            });

        }else {
            ToastUtils.showShort(LoginActivity.this,"网络异常，请检查您的网络~");
        }
    }
    //第三方   直接登录
    private void LoginThrid(String nickname,String type,String openid,String headsmall){
        if (NetWorkUtil.isNetworkConnected(this)){
            Log.i("第三方登录","直接登录"+"nickname...."+nickname+"type......"+type+"openid......"+openid+"headsmall......."+headsmall);
            Api.thirdLogin(nickname, type, openid,headsmall,new ApiResponseHandler(this) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    Log.i("第三方登录","直接登录"+"LoginActivity..."+apiResponse.toString());
                    if (apiResponse.getCode() == 0){
                        ToastUtils.showShort(LoginActivity.this,"成功");
                        ThridLogin thridLogin = JSON.parseObject(apiResponse.getData(), ThridLogin.class);
                        Log.i("第三方登录","直接登录"+"LoginActivity..."+thridLogin.getPhone());

                   //     JSONObject jsonObject = JSON.parseObject(apiResponse.getData());
                        LiveMySelfInfo.getInstance().setPhone(thridLogin.getPhone());
                        LiveMySelfInfo.getInstance().setId(thridLogin.getUid());
                        LoginManager.getInstance().setUserID(LoginActivity.this,thridLogin.getUid());
                        LiveMySelfInfo.getInstance().setNickName(thridLogin.getNickname());
                        LiveMySelfInfo.getInstance().setAvatar(thridLogin.getHeadsmall());
                        loginPresenter.cashData(apiResponse.getData());
                        loginPresenter.getUserSig(thridLogin.getPhone());

                    }else if (apiResponse.getCode() == -4){
                        ToastUtils.showLong(LoginActivity.this,apiResponse.getMessage());
                    }else {
                        ToastUtils.showShort(LoginActivity.this,apiResponse.getMessage());
                    }
                }
            });

        }else {
            ToastUtils.showShort(LoginActivity.this,"网络异常，请检查您的网络~");
        }
    }
    /**
     * 前往忘记密码界面
     */
    private void toForget() {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    /**
     * 前往注册页面
     */
    private void toRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, LoginPresenter.AUTO_LOGIN_REQUEST);
    }

    //    private boolean isPermissions;
    private AlertDialog dialog;

    private void BuilderDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.location_Permissions))
                .setTitle("权限提示")
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, RC_SETTINGS_SCREEN);
                    }
                }).setNegativeButton("取消", null);
        dialog = alertDialogBuilder.create();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LoginPresenter.AUTO_LOGIN_REQUEST && data != null) {
            String phone = data.getStringExtra("phone");
            String pwd = data.getStringExtra("pwd");
            loginPresenter.login(phone.trim(), pwd.trim()); //注册成功自动登录
        }
//        else if (requestCode == RC_SETTINGS_SCREEN) {
//            // Do something after user returned from app settings screen, like showing a Toast.
//            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
//                    .show();
//        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

//    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
//    public void locationAndContactsTask() {
//        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
//        if (EasyPermissions.hasPermissions(this, perms)) {
//            // Have permissions, do the thing!
//            initLocation();
////            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
//        } else {
//            // Ask for both permissions
//            EasyPermissions.requestPermissions(this, "需要获取您的权限",
//                    RC_LOCATION_CONTACTS_PERM, perms);
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
////        Log.i("onRequestPermissionsResult", requestCode + "  permissions  " + permissions[0] + "  grantResults  " + grantResults[0]);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//    }

//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Log.i("TAG", "onPermissionsGranted:" + requestCode + ":" + perms.size() + ":" + perms.get(0));
//        if (dialog != null && !dialog.isShowing()) {
//            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//            int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Process.myUid(), getPackageName());
//            if (checkOp == AppOpsManager.MODE_IGNORED) {
//                if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//                    Log.i("AppSettingsDialog", "AppSettingsDialog---------");
//                    dialog.show();
//                }
//            } else {
//                initLocation();
//            }
//        }
//    }

//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Log.i("TAG", "onPermissionsDenied:" + requestCode + ":" + perms.size() + ":" + perms.get(0));
//        if (dialog != null && !dialog.isShowing()) {
////            if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            Log.i("dialog", "-------dialog");
//            dialog.show();
////            }
////            ToastUtils.showShort(this, "请上设置里设置权限");
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.clearTask();
        UMShareAPI.get(this).release();
    }

    private long beginTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - beginTime <= 2000) {
            super.exitSetting();
            super.onBackPressed();
        } else {
            beginTime = System.currentTimeMillis();
            ToastUtils.showShort(this, getString(R.string.exit_app));
        }

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
}
