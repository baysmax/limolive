package com.example.project.limolive.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.fragment.FindGoodsFragment;
import com.example.project.limolive.fragment.FriendsFragment;
import com.example.project.limolive.fragment.FriendsStoreFragment;
import com.example.project.limolive.fragment.HomeFragment;
import com.example.project.limolive.fragment.HomeFragment2;
import com.example.project.limolive.fragment.MyFragment;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.presenters.LiveHelper;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomProgressDialog;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hpg on 2016/12/13;
 * 首页
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView tabs[];
    private LinearLayout tabs_ll[];
    private TextView tab_tv[];
    private BaseFragment fragments[];
    public static final String changFragment = "FRAGMENTCHANGETOHOME";
    private int currentIndex = 0;
    private int clickIndex = 0;

    private boolean isHideStatus = false; //是否隐藏status
    private LinearLayout ll_tabs;
    private CustomProgressDialog mProgressDialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tellService();
        checkUp();
        initView();
        setChangIMname();
        setChangIMHead();
        PgyCrashManager.register(this);
        PgyUpdateManager.register(this,getString(R.string.file_provider));

        //  checkUp();
    }

    private void tellService() {
        LiveHelper.tellstartExitRoom(getApplication());
    }

    private void initView() {
        tabs = new ImageView[5];
        tab_tv=new TextView[5];
        tabs_ll=new LinearLayout[5];

        tabs_ll[0] = (LinearLayout) findViewById(R.id.ll_home);
        tabs_ll[1] = (LinearLayout) findViewById(R.id.ll_friendshop);
        tabs_ll[2] = (LinearLayout) findViewById(R.id.ll_live);
        tabs_ll[3] = (LinearLayout) findViewById(R.id.ll_shopping);
        tabs_ll[4] = (LinearLayout) findViewById(R.id.ll_my);
        for (int i = 0; i < tabs_ll.length; i++) {
            tabs_ll[i].setOnClickListener(this);
        }


        tabs[0] = (ImageView) findViewById(R.id.iv_home);
        tabs[1] = (ImageView) findViewById(R.id.iv_friendshop);
        tabs[2] = (ImageView) findViewById(R.id.iv_live);
        tabs[3] = (ImageView) findViewById(R.id.iv_shopping);
        tabs[4] = (ImageView) findViewById(R.id.iv_my);

        tab_tv[0]= (TextView) findViewById(R.id.tv_home);
        tab_tv[1]= (TextView) findViewById(R.id.tv_friend);
        tab_tv[2]= (TextView) findViewById(R.id.tv_live);
        tab_tv[3]= (TextView) findViewById(R.id.tv_shopping);
        tab_tv[4]= (TextView) findViewById(R.id.tv_my);

        ll_tabs = (LinearLayout) findViewById(R.id.ll_tabs);
        ll_tabs.setVisibility(View.VISIBLE);

        fragments = new BaseFragment[5];
        fragments[0] = new HomeFragment2();
        fragments[1] = new FriendsFragment();
        fragments[3] = new FindGoodsFragment();
        fragments[4] = new MyFragment();
        // fragments[1] = new ContactFragment();
        loadFragment();
    }

    @Override
    public void onClick(View v) {
        isHideStatus = false;
        switch (v.getId()) {
            case R.id.ll_home: //主页
                clickIndex = 0;
                break;
            case R.id.ll_friendshop: //朋友商铺
                clickIndex = 1;
                break;
            case R.id.ll_live: //直播
                showProgressDialog("正在请求...");
                tabs[2].setEnabled(false);
                Api.isBaned(LoginManager.getInstance().getUserID(MainActivity.this), new ApiResponseHandler(MainActivity.this) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        tabs[2].setEnabled(true);
                        if (mProgressDialog!=null&&mProgressDialog.isShowing()){
                            mProgressDialog.dismiss();
                        }
                        if (apiResponse!=null&&apiResponse.getCode()==0){
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, BeforeLiveActivity.class);
                            overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                            startActivity(intent);
                        }else if (apiResponse.getCode()==-2){
                            showDialogs(apiResponse.getMessage());
                        }
                    }
                });

                break;
            case R.id.ll_shopping: //购物
                //clickIndex = 3;
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShoppingActivity.class);
                overridePendingTransition(R.anim.push_bottom_in, R.anim.push_bottom_out);
                startActivity(intent);
               break;
            case R.id.ll_my: //我的
                clickIndex = 4;
                break;
        }
        if (clickIndex == currentIndex) {
            return;
        }
        loadFragment();
    }
    public void showProgressDialog(String msg) {
        mProgressDialog = new CustomProgressDialog(this);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }
    Handler handler = new Handler();
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        String share = intent.getStringExtra("share");
        if ("share".equals(share)){
            share();
        }
        String s= intent.getStringExtra("isIntent");
        Log.i("飘屏动画","s="+s);
        if (s!=null&&!"".equals(s)){
            final String[] split = s.split(",");
            if (split.length==9){
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("飘屏动画","s=qd");
                        Intent intents = new Intent(MainActivity.this, LiveingActivity.class);
                        intents.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                        LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                        CurLiveInfo.setHostID(split[0]);
                        CurLiveInfo.setHostName(split[1]);
                        CurLiveInfo.setHostAvator(split[2]);
                        CurLiveInfo.setRoomNum(split[3]);
                        CurLiveInfo.setHost_phone(split[4]);
                        CurLiveInfo.setMembers(Integer.parseInt(split[5])); // 添加自己
                        CurLiveInfo.setAdmires(Integer.parseInt(split[6]));
                        CurLiveInfo.setAddress(split[7]);
                        CurLiveInfo.setTitle(split[8]);
                        startActivity(intents);
                    }
                },500);
            }
        }
    }

    private void showDialogs(String banDate) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("禁播通知：")
                .setMessage(banDate)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 加载Fragment R.id.framelayout color_57DDC5
     */
    private void loadFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragments[clickIndex].isAdded()) {
            ft.add(R.id.framelayout, fragments[clickIndex]);
        }
        ft.hide(fragments[currentIndex]);
        ft.show(fragments[clickIndex]);
        changeStyle();
        currentIndex = clickIndex;
        ft.commit();
        if (!isHideStatus) {
            setDefaultStatusBar();
        } else {
            ll_tabs.setVisibility(View.GONE);
            setStatusBarbg(R.drawable.status_bar);
        }
    }

    /**
     * 修改图片颜色
     */
    private void changeStyle() {
        if (currentIndex == 0) {
            tabs[0].setSelected(false);
            tab_tv[0].setTextColor(0x4b4a4a);
        }
        if (clickIndex == 0) {
            tabs[0].setSelected(true);
            tab_tv[0].setTextColor(0xa0fce13d);
        }
        tabs[currentIndex].setSelected(false);
        for (int i = 0; i < tab_tv.length; i++) {
            tab_tv[i].setTextColor(0xaa4b4a4a);
        }
        //tab_tv[currentIndex].setTextColor(0x4b4a4a);
        tabs[clickIndex].setSelected(true);
        tab_tv[clickIndex].setTextColor(0xa0fce13d);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PgyUpdateManager.unregister();
        PgyCrashManager.unregister();
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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    private void setChangIMname(){
        //设置新昵称为cat
        TIMFriendshipManager.getInstance().setNickName(LoginManager.getInstance().getHostName(this), new TIMCallBack(){
            @Override
            public void onError(int code, String desc){
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e("设置昵称", "setNickName failed: " + code + " desc");
            }

            @Override
            public void onSuccess(){
                Log.e("设置昵称", "setNickName succ");
            }
        });
    }

    private void setChangIMHead(){
        //设置IM头像
        String faceUrl = "";
        if (LoginManager.getInstance().getAvatar(this).contains("http://")){
            faceUrl=LoginManager.getInstance().getAvatar(this);
        }else {
            faceUrl= ApiHttpClient.API_URL+LoginManager.getInstance().getAvatar(this);
        }
        TIMFriendshipManager.getInstance().setFaceUrl(faceUrl, new TIMCallBack(){
            @Override
            public void onError(int code, String desc) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e("设置头像", "setFaceUrl failed: " + code + " desc" + desc);
            }

            @Override
            public void onSuccess() {
                Log.e("设置头像", "setFaceUrl succ");
            }
        });
    }

    /**
     * 分享
     */
    private void share() {
        UMImage thumb = new UMImage(this, R.mipmap.logo);
        UMWeb web = new UMWeb("https://www.pgyer.com/Ko1C");
        web.setTitle("柠檬秀");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("大家好,我正在直播哦，喜欢我的朋友赶紧来哦");//描述
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.SINA, SHARE_MEDIA.QQ)
                .withMedia(web)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(MainActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };
}
