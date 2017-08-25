package com.example.project.limolive.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.fragment.MyApplyFragment;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.helper.TakePhotoHelper;
import com.example.project.limolive.localalbum.common.ImageUtils;
import com.example.project.limolive.localalbum.common.LocalImageHelper;
import com.example.project.limolive.localalbum.ui.LocalAlbum;
import com.example.project.limolive.localalbum.widget.FilterImageView;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.ChangeDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by AAA on 2017/8/25.
 */

public class MyApplyActivity extends BaseActivity {

    private MyApplyFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applys);
        initView();
    }

    private  void initView(){
        loadTitle();
        fragment = new MyApplyFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,fragment).commit();
    }



    /**
     * 设置顶部标题栏颜色属性
     */
    private void loadTitle() {
        setTitleString("申请店铺");
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
