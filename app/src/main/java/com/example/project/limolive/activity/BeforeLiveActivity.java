package com.example.project.limolive.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.live.AllcatgoryBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.presenter.LoginPresenter;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.presenters.InitBusinessHelper;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.PhoneInfo;
import com.example.project.limolive.utils.SPUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.utils.datepicker.PermissionsActivity;
import com.example.project.limolive.utils.datepicker.PermissionsChecker;
import com.example.project.limolive.view.CustomProgressDialog;
import com.example.project.limolive.view.SelectFenleiiPopupWindow;
import com.tencent.av.sdk.AVContext;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.core.ILiveRoomManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：hpg on 2016/12/23 10:57
 * 功能：
 */
public class BeforeLiveActivity extends Activity implements  SurfaceHolder.Callback ,View.OnClickListener, SelectFenleiiPopupWindow.ChangeText {

    private boolean bPermission = false;
    private RelativeLayout rl_selectFenlei, rl_addLocation;
    private EditText et_live_tittle;
    private ImageView iv_out, iv_fenlei, iv_addlocation, iv_chat, iv_qq, iv_weibo, iv_colect, iv_StartLive;
    private LinearLayout all_layout;
    private ImageView iv_home;
    private ImageView iv_live;
    public static final String FENLEIINFO = "FENLEIINFO";
    private List<AllcatgoryBean> AllcatgoryBeans;
    private TextView tv_selectFenlei;
    private String selectType_name, SelectId;
    private SPUtil sp;
    private TextView id_city;
    private static final int REQUEST_PHONE_PERMISSIONS = 0;
    private int selectPositon;
    private CustomProgressDialog mProgressDialog;


    private ImageView back, position;//返回和切换前后置摄像头
    private SurfaceView surface;
    private SurfaceHolder holder;
    private  Camera camera;//声明相机
    private String filepath = "";//照片保存路径
    private int cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头
    private static final int CAMERA_OK=100;
    private PermissionsChecker mPermissionsChecker;
    private int bls=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏

        super.onCreate(savedInstanceState);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//没有标题


       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //SCREEN_ORIENTATION_BEHIND： 继承Activity堆栈中当前Activity下面的那个Activity的方向
        //SCREEN_ORIENTATION_LANDSCAPE： 横屏(风景照) ，显示时宽度大于高度
        //SCREEN_ORIENTATION_PORTRAIT： 竖屏 (肖像照) ， 显示时高度大于宽度
        //SCREEN_ORIENTATION_SENSOR  由重力感应器来决定屏幕的朝向,它取决于用户如何持有设备,当设备被旋转时方向会随之在横屏与竖屏之间变化
        //SCREEN_ORIENTATION_NOSENSOR： 忽略物理感应器——即显示方向与物理感应器无关，不管用户如何旋转设备显示方向都不会随着改变("unspecified"设置除外)
        //SCREEN_ORIENTATION_UNSPECIFIED： 未指定，此为默认值，由Android系统自己选择适当的方向，选择策略视具体设备的配置情况而定，因此不同的设备会有不同的方向选择
        //SCREEN_ORIENTATION_USER： 用户当前的首选方向
        dss();
        mPermissionsChecker = new PermissionsChecker(this);

    }

    private void dss() {
        //checkPermission();
        sp = SPUtil.getInstance(this);
        setContentView(R.layout.fragment_alive);

        surface();
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        boolean living = pref.getBoolean("living", false);
        Log.i("打印版本", "ssss" + AVContext.getVersion());

        // 提前更新sig
        bPermission = checkPublishPermission();
        ClearFenlei();
        initView();
      /*  if (living) {
            NotifyDialog dialog = new NotifyDialog();
            dialog.show(getString(R.string.title_living), getSupportFragmentManager(), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }*/
    }

    private void surface() {
        surface= (SurfaceView) findViewById(R.id.surface);
        surface.setSystemUiVisibility(View.INVISIBLE);
        holder = surface.getHolder();//获得句柄
        holder.addCallback(this);//添加回调
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//surfaceview不维护自己的缓冲区，等待屏幕渲染引擎将内容推送到用户面前
    }

    //检查权限
    void checkPermission() {
        Log.i("手机sdk版本", "Build.VERSION.SDK_INT..." + Build.VERSION.SDK_INT);
        final List<String> permissionsList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.i("手机sdk版本", "Build.VERSION.SDK_INT..." + Build.VERSION.SDK_INT);
            if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.CAMERA);
            }
            if ((checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.RECORD_AUDIO);
            }
            if ((checkSelfPermission(Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.WAKE_LOCK);
            }
            if ((checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS) != PackageManager.PERMISSION_GRANTED)) {
                permissionsList.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            }
            if (permissionsList.size() != 0) {
                Log.i("手机sdk版本", "permissionsList.size()" + permissionsList.size());
//                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
//                        REQUEST_PHONE_PERMISSIONS);
            }
        }
    }

    protected void initView() {
        //setStatusBarbg(R.drawable.status_bar);
        //title_bar_standard = (LiveMallTitleBar) findViewById(R.id.title_bar_standard);
        iv_out = (ImageView) findViewById(R.id.iv_out);
        et_live_tittle = (EditText) findViewById(R.id.et_live_tittle);
        iv_fenlei = (ImageView) findViewById(R.id.iv_fenlei);
        rl_selectFenlei = (RelativeLayout) findViewById(R.id.rl_selectFenlei);
        tv_selectFenlei = (TextView) findViewById(R.id.tv_selectFenlei);
        rl_addLocation = (RelativeLayout) findViewById(R.id.rl_addLocation);
        id_city = (TextView) findViewById(R.id.id_city);
        iv_addlocation = (ImageView) findViewById(R.id.iv_addlocation);
        iv_chat = (ImageView) findViewById(R.id.iv_chat);
        iv_qq = (ImageView) findViewById(R.id.iv_qq);
        iv_weibo = (ImageView) findViewById(R.id.iv_weibo);
        iv_colect = (ImageView) findViewById(R.id.iv_colect);
        iv_StartLive = (ImageView) findViewById(R.id.iv_StartLive);
        all_layout = (LinearLayout) findViewById(R.id.all_layout);
        AllcatgoryBeans = new ArrayList<>();
        BindEvent();
    }

    protected void BindEvent() {
        iv_out.setOnClickListener(this);
        rl_selectFenlei.setOnClickListener(this);
        rl_addLocation.setOnClickListener(this);
        iv_chat.setOnClickListener(this);
        iv_qq.setOnClickListener(this);
        iv_weibo.setOnClickListener(this);
        iv_colect.setOnClickListener(this);
        iv_StartLive.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (camera!=null){
            camera.stopPreview();
            camera.release();
        }
        camera = null;
        bls=0;
        holder = null;
        surface = null;
        // unregisterReceiver(Receiver);
    }

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this,
                        (String[]) permissions.toArray(new String[0]),
                        Constants.WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.iv_out:
                finish();
                break;
            case R.id.rl_selectFenlei://选择分类
                mProgressDialog = new CustomProgressDialog(BeforeLiveActivity.this);
                // showProgressDialog("正在加载");
                //  SelectFenlei();
                SelectFenleiiPopupWindow aaaa = new SelectFenleiiPopupWindow(BeforeLiveActivity.this, this, selectPositon);
                aaaa.showPopupWindow(rl_selectFenlei);
                break;
            case R.id.rl_addLocation://添加定位
                // mLocationClient.start();
                String locationAddress = sp.getString("LocationAddress");
                id_city.setText(locationAddress);
                Log.i("rl_addLocation", "------------rl_addLocation" + locationAddress);
                Log.i("rl_addLocation", "------------rl_addLocation" + sp.getString("lon"));
                Log.i("rl_addLocation", "------------rl_addLocation" + sp.getString("lat"));
                break;
            case R.id.iv_chat:
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_weibo:
                break;
            case R.id.iv_colect:
                break;
            case R.id.iv_StartLive:
                if (TextUtils.isEmpty(et_live_tittle.getText())) {
                    ToastUtils.showShort(this, "标题不能为空额");
                } /*else if (TextUtils.isEmpty(tv_selectFenlei.getText())) {
                    ToastUtils.showShort(this, "请选择直播类型");
                } */else {
                    if (ILiveSDK.getInstance().getAVContext() == null) {//retry
                        //ToastUtils.showShort(this, "版本为空 imsdk登录失败");
                        retryImLogin();
                    }
                    intent.setClass(this, LiveingActivity.class);
                    intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                    LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);//身份为主播
                    LiveMySelfInfo.getInstance().setJoinRoomWay(true);
                    CurLiveInfo.setTitle(et_live_tittle.getText().toString());//标题
                    CurLiveInfo.setLiveType(SelectId);
                    CurLiveInfo.setHostName(LoginManager.getInstance().getHostName(this));
                    CurLiveInfo.setHostID(LiveMySelfInfo.getInstance().getId());//主播id
                    CurLiveInfo.setHost_phone(LiveMySelfInfo.getInstance().getPhone());//主播id
                    CurLiveInfo.setRoomNum(LiveMySelfInfo.getInstance().getMyRoomNum());//房间号
                    LiveMySelfInfo.getInstance().setAvatar(LoginManager.getInstance().getAvatar(this));
                    CurLiveInfo.setHostAvator(LiveMySelfInfo.getInstance().getAvatar());
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        retryImLogin();
        ClearFenlei();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ILiveRoomManager.getInstance().onResume();
        surface();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ILiveRoomManager.getInstance().onPause();
    }

    private void retryImLogin() {
        if (ILiveSDK.getInstance().getAVContext() == null) {//retry
            InitBusinessHelper.initApp(this.getApplicationContext());
            LoginPresenter mLoginHelper = new LoginPresenter(this);
            mLoginHelper.imLogin(LiveMySelfInfo.getInstance().getId(), LiveMySelfInfo.getInstance().getUserSig());
        }
    }

    private void SelectFenlei() {
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, "请检查网络!");
            return;
        }
        Api.getGoodsType(new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Log.i("所有分类", apiResponse.toString());

                    List<AllcatgoryBean> list = JSON.parseArray(apiResponse.getData(), AllcatgoryBean.class);
                    AllcatgoryBeans.clear();
                    AllcatgoryBeans.addAll(list);
                    Log.i("所有分类", AllcatgoryBeans.toString());
                    //hideProgressDialog();
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                ToastUtils.showCustom(BeforeLiveActivity.this, errMessage, Toast.LENGTH_SHORT);
                //hideProgressDialog();
            }
        });
    }

    //清除选择分类中保存的信息
    private void ClearFenlei() {
        SharedPreferences settings = getSharedPreferences(Constants.USER_INFO, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("selectPosition", -1);
        editor.putString("selectId", "");
        editor.putString("selectName", "");
        editor.commit();
        Log.i("清除信息", "走了么");
    }

    @Override
    public void changeText(String text, int selectPositon) {
        tv_selectFenlei.setText(text);
        this.selectPositon = selectPositon;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }
    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private static final int REQUEST_CODE = 0; // 请求码
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }else {
            initCaer();
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }else {
            initCaer();
        }

    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }
    private void initCaer() {
            camera=null;
            camera = Camera.open();
            initCaera();
                try {
                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                camera.startPreview();//开始预览

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private void initCaera() {

        //切换前后摄像头
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

        for(int i21 = 0; i21 < cameraCount; i21++   ) {
            Camera.getCameraInfo(i21, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 1) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i21);//打开当前选中的摄像头
                    Camera.Parameters parameters = camera.getParameters();
                    Camera.Size preSize = getCloselyPreSize(true, surface.getWidth(), surface.getHeight(), parameters.getSupportedPreviewSizes());
                    if (parameters!=null&&preSize!=null){
                        parameters.setPreviewSize(preSize.width, preSize.height);
                    }
                    camera.setParameters(parameters);
                    try {
                        camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Log.i("直播", "手机型号="+PhoneInfo.getPhoneBrand());
                    if ("Xiaomi".equals(PhoneInfo.getPhoneBrand())
                            ||"xiaomi".equals(PhoneInfo.getPhoneBrand())
                            ||"MIUI".equals(PhoneInfo.getPhoneBrand())){
                        camera.setDisplayOrientation(270);//旋转90度
                    }else {
                        camera.setDisplayOrientation(90);
                    }
                    //setCameraDisplayOrientation(BeforeLiveActivity.this,0,camera);
                    camera.startPreview();//开始预览
                    cameraPosition = 0;
                    break;
                }
            }
        }
    }
    /**
     * 通过对比得到与宽高比最接近的预览尺寸（如果有相同尺寸，优先选择）
     *
     * @param isPortrait 是否竖屏
     * @param surfaceWidth 需要被进行对比的原宽
     * @param surfaceHeight 需要被进行对比的原高
     * @param preSizeList 需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public   Camera.Size getCloselyPreSize(boolean isPortrait, int surfaceWidth, int surfaceHeight, List<Camera.Size> preSizeList) {
        int reqTmpWidth;
        int reqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if (isPortrait) {
            reqTmpWidth = surfaceHeight;
            reqTmpHeight = surfaceWidth;
        } else {
            reqTmpWidth = surfaceWidth;
            reqTmpHeight = surfaceHeight;
        }
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for(Camera.Size size : preSizeList){
            if((size.width == reqTmpWidth) && (size.height == reqTmpHeight)){
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) reqTmpWidth) / reqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }
    public static void setCameraDisplayOrientation ( Activity activity ,
                                                     int cameraId , android.hardware.Camera camera ) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo ( cameraId , info );
        int rotation = activity.getWindowManager ().getDefaultDisplay ().getRotation ();
        int degrees = 0 ;
        switch ( rotation ) {
            case Surface.ROTATION_0 : degrees = 0 ; break ;
            case Surface.ROTATION_90 : degrees = 90 ; break ;
            case Surface.ROTATION_180 : degrees = 180 ; break ;
            case Surface.ROTATION_270 : degrees = 270 ; break ;
        }

        int result ;
        if ( info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT ) {
            result = ( info.orientation + degrees ) % 360 ;
            result = ( 360 - result ) % 360 ;   // compensate the mirror
        } else {   // back-facing
            result = ( info.orientation - degrees + 360 ) % 360 ;
        }
        camera.setDisplayOrientation ( result );
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
