package com.example.project.limolive.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.view.CustomDialog;

/**
 * @author 黄亚菲 on 2017/1/6.
 * 联系客服
 */
public class ContactCustomerActivity extends BaseActivity implements View.OnClickListener{

    //微信  QQ  电话
    private RelativeLayout rl_wechat_customer,rl_qq_customer,rl_tel_customer;

    private CustomDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_customer);
        initView();
        monitor();
    }

    private void initView(){
        loadTitle();
        rl_qq_customer = (RelativeLayout) findViewById(R.id.rl_qq_customer);
        rl_tel_customer = (RelativeLayout) findViewById(R.id.rl_tel_customer);
        rl_wechat_customer = (RelativeLayout) findViewById(R.id.rl_wechat_customer);
    }

    private void monitor(){
        rl_wechat_customer.setOnClickListener(this);
        rl_tel_customer.setOnClickListener(this);
        rl_qq_customer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.rl_wechat_customer:
                break;
            case  R.id.rl_tel_customer:
                callKeFu();
                break;
            case  R.id.rl_qq_customer:
                break;


        }
    }

    private void callKeFu() {
        builder = new CustomDialog.Builder(this);
        builder.setMessage("是否联系客服 10086");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("拨打", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestPermissions();
            }
        });
        builder.create().show();
    }

    /*****
     * 打电话的权限
     */
    public void requestPermissions() {
        //判断当前是否获取到打电话的权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "please give me the permission", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:17600113823"));
            startActivity(intent);
        }
    }




    private void loadTitle() {
        setTitleString(getString(R.string.contact_coustomer));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
