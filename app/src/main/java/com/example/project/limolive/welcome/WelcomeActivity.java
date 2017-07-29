package com.example.project.limolive.welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.activity.MainActivity;
import com.example.project.limolive.presenter.WellComeHelper;
import com.example.project.limolive.tencentlive.adapters.WelConmeAdapter;
import com.example.project.limolive.welcome.ViewInterface.IWelconme;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 欢迎页by hwj in 2016/08/31
 */
public class WelcomeActivity extends Activity implements IWelconme {

    private WellComeHelper helper;
    private ViewPager mViewpager;
    private ArrayList<Integer> imageList;
    private WelConmeAdapter ivAdapter;
    private LinearLayout linearLayout;
    private RelativeLayout relativeLayout;



    public void setPosition(int position) {
        this.position = position;

    }

    private int position;
//    private LocationClient mLocationClient = null;
//    private BDLocationListener myListener = new MyLocationListener();
//    private SPUtil sp;
//    private Double lon, lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

//        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        sp = SPUtil.getInstance(this);
//        initLocation();
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数
//        mLocationClient.start();
//        requestPermission();
      //  PgyCrashManager.register(this);
      //  PgyUpdateManager.register(this,getString(R.string.file_provider));
      //  checkUp();
        initView();
        initImage();
        initDate();
        helper = new WellComeHelper(this);
     //   helper.startAlpha(findViewById(R.id.ll_root_layout));
        helper.intoNext(this);
    }

    private void initView() {
        mViewpager=findViewById(R.id.vp_Welcome_images);
        linearLayout=findViewById(R.id.welcome_to_login);
        relativeLayout=findViewById(R.id.ll_start);
    }


    private void initDate() {
        ivAdapter=new WelConmeAdapter(this,imageList);

        mViewpager.setAdapter(ivAdapter);
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==2){
                    showBtn();
                };
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private void initImage() {
        imageList=new ArrayList<>();
        imageList.add(R.drawable.item1);
        imageList.add(R.drawable.item2);
        imageList.add(R.drawable.item3);
    }





    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mLocationClient.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  PgyUpdateManager.unregister();
      //  PgyCrashManager.unregister();
    }

    private void checkUp(){
        // 版本检测方式2：带更新回调监听
        PgyUpdateManager.register(WelcomeActivity.this,getString(R.string.file_provider),
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        Log.i("主人有新的版本更新哟",result.toString());
                        new AlertDialog.Builder(WelcomeActivity.this)
                                .setTitle("更新")
                                .setMessage("主人有新的版本更新哟...")
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                String url;
                                                JSONObject jsonData;
                                                try {
                                                    jsonData = new JSONObject(
                                                            result);
                                                    if ("0".equals(jsonData
                                                            .getString("code"))) {
                                                        JSONObject jsonObject = jsonData
                                                                .getJSONObject("data");
                                                        url = jsonObject
                                                                .getString("downloadURL");

                                                        startDownloadTask(
                                                                WelcomeActivity.this,
                                                                url);
                                                    }

                                                } catch (JSONException e) {
                                                    // TODO Auto-generated
                                                    // catch block
                                                    e.printStackTrace();
                                                }
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
    public void showViewPager() {
        mViewpager.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBtn() {
        linearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBtn() {
        linearLayout.setVisibility(View.GONE);

    }

    @Override
    public void hideViewPager() {
        mViewpager.setVisibility(View.GONE);
    }

    @Override
    public void setWelcomeActivityBackground() {
        relativeLayout.setBackground(getDrawable(R.drawable.item3));
    }

    @Override
    public int getAdapterItem() {
        return position;
    }


    public void onClick(View view) {
        helper.comeToLogin();
    }
}
