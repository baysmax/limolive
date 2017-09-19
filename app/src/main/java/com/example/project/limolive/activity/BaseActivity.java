package com.example.project.limolive.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.example.project.limolive.R;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.helper.ActivityHelper;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.presenters.viewinface.ProfileView;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.utils.LogConstants;
import com.example.project.limolive.tencentlive.utils.SxbLog;
import com.example.project.limolive.utils.AfterPermissionGranted;
import com.example.project.limolive.utils.EasyPermissions;
import com.example.project.limolive.utils.SPUtil;
import com.example.project.limolive.view.CustomProgressDialog;
import com.example.project.limolive.widget.LiveMallTitleBar;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.tencent.TIMUserProfile;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * 本Activity承担管理众多Activity的任务，存储相关公共变量，以及初始化相关组件等，主要功能：
 * <p>兼容4.4以上版本，配置着色式状态栏一体化,各个Activity如果想要更改状态栏颜色的话需要调用setStatusBarColor(int Color)
 * 并传入色值即可</p>
 * <p>在加载fragment的时候可以直接调用 loadFragment()方法无需重复去获取Fragment管理者，
 * 详情请见本类的使用示例{@link ShoppingCartActivity}</p>
 *
 * @author hwj on 2016/12/13.
 */

public class BaseActivity extends AppCompatActivity implements ProfileView, EasyPermissions.PermissionCallbacks {
    private BroadcastReceiver recv;
    private CustomProgressDialog mProgressDialog;
    /**
     * 默认状态栏颜色
     */
    //public static final int STATUS_BAR_COLOR_DEFAULT = Color.parseColor("#00fce13d");
    public static final int STATUS_BAR_COLOR_DEFAULT = Color.parseColor("#44000000");
    //public static final int STATUS_BAR_COLOR_DEFAULT = Color.parseColor("#00ffffff");

    /**
     * 顶部标题栏
     */
    protected LiveMallTitleBar title_bar_standard;

    /**
     * 帮助销毁Activity的管理类
     */
    protected ActivityHelper activityHelper;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private SPUtil sp;
    private Double lon, lat;

    private Display display;

    public static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    public static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityHelper = ActivityHelper.getInstance();
        sp = SPUtil.getInstance(this);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //  initLocation();
        initBaseView();
        PgyCrashManager.register(this);


        activityHelper.onCreate(this);//当前Activity入栈

        recv = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.BD_EXIT_APP)) {
                    SxbLog.d("BaseActivity", LogConstants.ACTION_HOST_KICK + LogConstants.DIV + LiveMySelfInfo.getInstance().getId() + LogConstants.DIV + "on force off line");
                    finish();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.BD_EXIT_APP);

        registerReceiver(recv, filter);

        PgyCrashManager.register(this);
        //PgyUpdateManager.register(this,getString(R.string.file_provider));
        setDefaultStatusBar();

    }

    private void checkUps() {
        new AlertDialog.Builder(BaseActivity.this)
                .setTitle("更新")
                .setMessage("主人有新的版本更新哟...")
                .setNegativeButton(
                        "确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog, int which) {
                                Log.i("123456","android.intent.action.VIEW");
                                Intent intent= new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse("https://www.pgyer.com/Ko1C");
                                intent.setData(content_url);
                                startActivity(intent);
                            }
                        })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    protected void checkUp(){
        // 版本检测方式2：带更新回调监听
        PgyUpdateManager.register(BaseActivity.this,getString(R.string.file_provider),
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        //Log.i("主人有新的版本更新哟",result.toString());
                        new AlertDialog.Builder(BaseActivity.this)
                                .setTitle("更新")
                                .setMessage("主人有新的版本更新哟...")
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog, int which) {
//                                                String url;
//                                                JSONObject jsonData;
//                                                try {
//                                                    jsonData = new JSONObject(
//                                                            result);
//                                                    if ("0".equals(jsonData
//                                                            .getString("code"))) {
//                                                        JSONObject jsonObject = jsonData
//                                                                .getJSONObject("data");
//                                                        url = jsonObject
//                                                                .getString("downloadURL");
//
//                                                        startDownloadTask(
//                                                                BaseActivity.this,
//                                                                url);
//
//                                                    }
//
//                                                } catch (JSONException e) {
//                                                    // TODO Auto-generated
//                                                    // catch block
//                                                    e.printStackTrace();
//                                                }
                                                Log.i("123456","android.intent.action.VIEW");
                                                Intent intent= new Intent();
                                                intent.setAction("android.intent.action.VIEW");
                                                Uri content_url = Uri.parse("https://www.pgyer.com/Ko1C");
                                                        intent.setData(content_url);
                                                startActivity(intent);
                                            }
                                        })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        //  ToastUtils.showCustom(MainActivity.this, "已经是最新版本", Toast.LENGTH_SHORT);
                    }
                });
    }
    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(recv);
        } catch (Exception e) {
        }
        //PgyUpdateManager.unregister();

        PgyUpdateManager.unregister();
        PgyCrashManager.unregister();
        super.onDestroy();
    }

    private void initBaseView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            super.setContentView(R.layout.activity_base);
        }
        BuilderDialog();
        requestPermission();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        if (Build.VERSION.SDK_INT >= KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setUpKitkatColor(STATUS_BAR_COLOR_DEFAULT, layoutResID);
        } else {
            setLowKitkatColor(layoutResID);
        }
        loadTitleBar();
    }


    @Override
    protected void onPause() {
        super.onPause();
        //PgyFeedbackShakeManager.unregister();
        mLocationClient.stop();
    }


    @Override
    protected void onResume() {
        //PgyFeedbackShakeManager.register(this, false);
        super.onResume();
    }


    /**
     * 初始化标题栏
     */
    private void loadTitleBar() {
        title_bar_standard = (LiveMallTitleBar) findViewById(R.id.title_bar_standard);
    }


    /**
     * 4.4以上包含4.4的沉浸式状态栏
     *
     * @param resColor
     * @param layoutResID
     */
    private void setUpKitkatColor(int resColor, int layoutResID) {
        View group = findViewById(R.id.ll_root_layout);
        setStatusBar(group, resColor);
        ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(layoutResID, (ViewGroup) group, true);
    }

    /**
     * 4.4以下
     * @param layoutResID
     */
    private void setLowKitkatColor(int layoutResID) {
//        super.setContentView(layoutResID);
   }


    /**
     * 获取状态栏高度并设置
     */
    public void setStatusBar(View group, int resColor) {
        ImageView tool_bar = (ImageView) group.findViewById(R.id.tool_status_bar);
        //tool_bar.setBackgroundColor(resColor);
        int statusBarHeight1 = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
            ViewGroup.LayoutParams params = tool_bar.getLayoutParams();
            params.height = statusBarHeight1;
            tool_bar.setLayoutParams(params);
        }
    }

    /**
     * 设置状态栏颜色
     *
     * @param resColor Color
     */
    protected void setStatusBarColor(int resColor) {
        if (Build.VERSION.SDK_INT >= KITKAT) { //4.4以上版本
            ViewGroup group = (ViewGroup) findViewById(R.id.ll_root_layout);
            ImageView tool_bar = (ImageView) group.findViewById(R.id.tool_status_bar);
            tool_bar.setVisibility(View.VISIBLE);
            tool_bar.setImageResource(0);
            tool_bar.setBackgroundColor(resColor);
        }
    }

    /**
     * 设置状态栏背景
     */
    protected void setStatusBarbg(int bg) {
        if (Build.VERSION.SDK_INT >= KITKAT) { //4.4以上版本
            ViewGroup group = (ViewGroup) findViewById(R.id.ll_root_layout);
            ImageView tool_bar = (ImageView) group.findViewById(R.id.tool_status_bar);
            tool_bar.setVisibility(View.VISIBLE);
//          tool_bar.setBackground(getResources().getDrawable(bg));
            tool_bar.setImageResource(bg);
        }
    }

    /**
     * 设置默认的状态栏颜色
     */
    protected void setDefaultStatusBar() {
        setStatusBarColor(STATUS_BAR_COLOR_DEFAULT);
    }

    /**
     * 隐藏状态栏
     */
    protected void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= KITKAT) { //4.4以上版本
            ViewGroup group = (ViewGroup) findViewById(R.id.ll_root_layout);
            ImageView tool_bar = (ImageView) group.findViewById(R.id.tool_status_bar);
            tool_bar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题
     *
     * @param titleString
     */
    protected void setTitleString(String titleString) {
        if (title_bar_standard != null) {
            title_bar_standard.setTitle(titleString);
        }
    }
    protected void setTitleString(String titleString,float size,int color) {
        if (title_bar_standard != null) {
            title_bar_standard.setTitle(titleString,size,color);
        }
    }

    protected void setTitleString(int resString) {
        if (title_bar_standard != null) {
            title_bar_standard.setTitle(getString(resString));
        }
    }

    /**
     * 设置右边字体
     */
    protected void setRightText(String rightText) {
        if (title_bar_standard != null) {
            title_bar_standard.setRightText(rightText);
        }
    }
    /**
     * 设置背景颜色
     */
    protected void setBackgroundColor(int color) {
        if (title_bar_standard != null) {
            title_bar_standard.setBackgroundColor(color);
        }
    }

    /**
     * 配置图片控件
     *
     * @param resImg
     */
    protected void setLeftImage(int resImg) {
        if (title_bar_standard != null) {
            title_bar_standard.setLeftImageResource(resImg);
        }
    }

    protected void setRightImage(int resImg) {
        if (title_bar_standard != null) {
            title_bar_standard.setRightImageResource(resImg);
        }
    }

    /**
     * 设置左右两边控件点击事件
     *
     * @param clickListener
     */
    protected void setLeftRegionListener(View.OnClickListener clickListener) {
        if (title_bar_standard != null) {
            title_bar_standard.setLeftLayoutClickListener(clickListener);
        }
    }

    protected void setRightRegionListener(View.OnClickListener clickListener) {
        if (title_bar_standard != null) {
            title_bar_standard.setRightLayoutClickListener(clickListener);
        }
    }

//--------------------------------------------------------------------------------

    @Override
    public void updateProfileInfo(TIMUserProfile profile) {
        if (null != profile) {
            LiveMySelfInfo.getInstance().setAvatar(profile.getFaceUrl());
            if (!TextUtils.isEmpty(profile.getNickName())) {
                LiveMySelfInfo.getInstance().setNickName(profile.getNickName());
            } else {
                LiveMySelfInfo.getInstance().setNickName(profile.getIdentifier());
            }
        }
    }

    @Override
    public void updateUserInfo(int reqid, List<TIMUserProfile> profiles) {
    }

    /**
     * 加载Fragment
     *
     * @param fragment     需要加载的fragment
     * @param resContainer 相关容器
     */
    protected void loadFragment(BaseFragment fragment, int resContainer) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(resContainer, fragment);
        transaction.commit();
    }

    /**
     * 退出配置
     */
    protected void exitSetting() {

    }

    public void showProgressDialog(String msg) {
        mProgressDialog = new CustomProgressDialog(this);
        mProgressDialog.setMessage(msg);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**********
     * 申请权限
     */
    public void requestPermission() {
        locationAndContactsTask();
    }

    //在BaseActivity中也可以不开启此dialog,如若开启可能用户的体验会差一些,主要还是跟着需求来做
    private AlertDialog dialog;

    private void BuilderDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.rationale_ask_again))
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

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void locationAndContactsTask() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permissions, do the thing!
            initLocation();
//            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, "需要获取您的权限",
                    RC_LOCATION_CONTACTS_PERM, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
//            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
//                    .show();
        }
    }

    public static final int RC_LOCATION_CONTACTS_PERM = 124;
    public static final int RC_SETTINGS_SCREEN = 125;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {


                    } else {
                    }

                }
            }
            case REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "允许读写存储！", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, "未允许读写存储！", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Log.i("onRequestPermissionsResult", requestCode + "  permissions  " + permissions[0] + "  grantResults  " + grantResults[0]);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        Log.i("TAG", "onPermissionsGranted:" + requestCode + ":" + perms.size() + ":" + perms.get(0));
        if (dialog != null && !dialog.isShowing()) {
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Process.myUid(), getPackageName());
            if (checkOp == AppOpsManager.MODE_IGNORED) {
                if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
                    Log.i("AppSettingsDialog", "AppSettingsDialog---------");
                    dialog.show();
                }
            } else {
                initLocation();
            }
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
//        Log.i("TAG", "onPermissionsDenied:" + requestCode + ":" + perms.size() + ":" + perms.get(0));
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Log.i("dialog", "-------dialog");
            if (dialog != null && !dialog.isShowing()) {
                dialog.show();
            }
        }
    }

    protected void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mLocationClient.start();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            sb.append("\ncity : ");
            sb.append(location.getCity() + "   " + location.getAddress().district);

//            Log.e("城市",location.getCity());
//            Log.e("地址",location.getAddress()+"");

            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");


                Log.e("离线定位经度0", location.getLongitude() + "度");
                Log.e("离线定位维度0", location.getLatitude() + "度");


            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                lon = location.getLongitude();
                lat = location.getLatitude();


                Log.e("网络定位经度1", lon + "度");
                Log.e("网络定位维度1", lat + "度");


                sp.setString("LocationAddress", location.getCity());
                sp.setString("lon", String.valueOf(lon));
                sp.setString("lat", String.valueOf(lat));
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                lon = location.getLongitude();
                lat = location.getLatitude();


                Log.e("离线定位经度2", lon + "度");
                Log.e("离线定位维度2", lat + "度");

                sp.setString("LocationAddress", location.getCity());
                sp.setString("lon", String.valueOf(lon));
                sp.setString("lat", String.valueOf(lat));
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }
}
