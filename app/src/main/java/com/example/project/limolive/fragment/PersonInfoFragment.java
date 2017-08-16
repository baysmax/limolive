package com.example.project.limolive.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.ChangeInfoActivity;
import com.example.project.limolive.activity.LoginActivity;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.helper.ActivityHelper;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.helper.PermissionHelper;
import com.example.project.limolive.helper.SDHelper;
import com.example.project.limolive.helper.TakePhotoHelper;
import com.example.project.limolive.model.LoginModel;
import com.example.project.limolive.presenter.PersonInfoPresenter;
import com.example.project.limolive.provider.MineDataProvider;
import com.example.project.limolive.utils.datepicker.DateUtil;
import com.example.project.limolive.widget.ChangeDialog;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 个人资料
 * @author hwj on 2016/12/15.
 */

public class PersonInfoFragment extends BaseFragment implements View.OnClickListener{

    private MineDataProvider provider;

    private SimpleDraweeView iv_user_avatar;//头像
    private TextView tv_user_name;//用户名
    private TextView tv_live_code;//直播号
    private TextView tv_user_sex;//性别
    private TextView tv_user_age;//年龄
    private TextView tv_user_home;//家乡
    private TextView tv_user_profession;//职业
    private TextView tv_user_address;//地址
    private TextView tv_login_out;//退出登录

    //拍照工具
    private TakePhotoHelper takePhotoHelper;
    //个人信息提交工具
    private PersonInfoPresenter personInfoPresenter;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_person_info,inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        takePhotoHelper=new TakePhotoHelper(getActivity());
        provider=new MineDataProvider(getApplication());
        personInfoPresenter=new PersonInfoPresenter(getActivity());
        iv_user_avatar= (SimpleDraweeView) findViewById(R.id.iv_user_avatar);
        tv_user_name= (TextView) findViewById(R.id.tv_user_name);
        tv_live_code= (TextView) findViewById(R.id.tv_live_code);
        tv_user_sex= (TextView) findViewById(R.id.tv_user_sex);
        tv_user_age= (TextView) findViewById(R.id.tv_user_age);
        tv_user_home= (TextView) findViewById(R.id.tv_user_home);
        tv_user_profession= (TextView) findViewById(R.id.tv_user_profession);
        tv_user_address= (TextView) findViewById(R.id.tv_user_address);
        tv_login_out= (TextView) findViewById(R.id.tv_login_out);
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.rl_user_avatar).setOnClickListener(this);
        findViewById(R.id.rl_user_name).setOnClickListener(this);
        findViewById(R.id.rl_user_profession).setOnClickListener(this);
        findViewById(R.id.rl_user_age).setOnClickListener(this);
        findViewById(R.id.rl_user_home).setOnClickListener(this);
        findViewById(R.id.rl_user_sex).setOnClickListener(this);
        findViewById(R.id.rl_user_address).setOnClickListener(this);
        tv_login_out.setOnClickListener(this);
    }

    /**
     * 加载本地信息
     */
    private void loadPersonInfo() {
        LoginModel model=provider.getMineInfo(LoginManager.getInstance().getPhone(getApplication()));
        if(model!=null){
            iv_user_avatar.setImageURI(ApiHttpClient.API_PIC+model.getHeadsmall());
            tv_user_name.setText(model.getNickname());
            tv_live_code.setText(model.getLivenum());
            if("0".equals(model.getSex())){
                tv_user_sex.setText("未填写");
            }else
            tv_user_age.setText(model.getBirthday());
            tv_user_home.setText(model.getProvince()+" "+model.getCity());
            tv_user_profession.setText(model.getJob());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.rl_user_avatar: //
                changeUserHead();
                break;
            case R.id.rl_user_name: //修改用户名
                enterChangeActivity(ChangeInfoActivity.SIMPLE,ChangeSimpleInfoFragment.SIMPLE_INFO_USER_NAME);
                break;
            case R.id.rl_user_profession: //修改职业
                enterChangeActivity(ChangeInfoActivity.SIMPLE,ChangeSimpleInfoFragment.SIMPLE_INFO_JOB);
                break;
            case R.id.rl_user_age: //年龄
                showAgeDialog();
                break;
            case R.id.rl_user_home: //家乡
                showAddressDialog();
                break;
            case R.id.rl_user_sex: //性别
                changeSex();
                break;
            case R.id.rl_user_address: //修改地址页面
                enterChangeActivity(ChangeInfoActivity.ADDRESS);
                break;
            case R.id.tv_login_out: //退出登录
                loginOut();
                break;
        }
    }
    /**
     * 退出登录，登录见{@link com.example.project.limolive.helper.LoginManager},
     * {@link com.example.project.limolive.presenter.LoginPresenter}
     */
    private void loginOut() {
        //TODO 退出相关SDK
        LoginManager.getInstance().loginOut(getApplication());
        MineDataProvider provider = new MineDataProvider(getApplication());
        provider.deleteAllTables();
        provider.reBuildData();
        getApplication().destroyAllActivity(new Handler());
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    /**
     * 修改性别
     */
    private void changeSex() {
        final ChangeDialog dialog = DialogFactory.getBottomSelectDialog(getActivity(),ChangeDialog.SEX_SELECT);
        dialog.show();
        dialog.setCheckedFirst(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personInfoPresenter.changeSex("1",tv_user_sex);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        dialog.setCheckedSecond(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personInfoPresenter.changeSex("2",tv_user_sex);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        dialog.setCheckedThird(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }

    private void showAddressDialog() {
        personInfoPresenter.showAddressDialog(tv_user_home);
    }
    private void showAgeDialog() {
        //选择出生日期
        personInfoPresenter.showDateDialog(DateUtil.getDateForString("1990-01-01"),tv_user_age);
    }

    private void enterChangeActivity(int args1,int args2){
        Intent intent=new Intent(getActivity(), ChangeInfoActivity.class);
        intent.putExtra(ChangeInfoActivity.INFO_TYPE,args1);
        intent.putExtra(ChangeSimpleInfoFragment.SIMPLE_INFO_TEXT,args2);
        startActivity(intent);
    }
    private void enterChangeActivity(int args){
        Intent intent=new Intent(getActivity(), ChangeInfoActivity.class);
        intent.putExtra(ChangeInfoActivity.INFO_TYPE,args);
        startActivity(intent);
    }

    /**
     * 显示选择图片类型并注册相应点击事件
     */
    private void changeUserHead() {
        final ChangeDialog dialog = DialogFactory.getDefaultSelectDialog(getActivity());
        dialog.show();
        dialog.setCheckedFirst(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoHelper.takePhotosFromFragment(PersonInfoFragment.this);
            }
        });
        dialog.setCheckedSecond(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoHelper.openSystemAlbumFromFragment(PersonInfoFragment.this);
            }
        });
        dialog.setCheckedThird(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case TakePhotoHelper.REQUEST_IMAGE_CAPTURE: //拍照
                takePhotoHelper.handleSavedImage(this);
                break;
            case TakePhotoHelper.REQUEST_ALBUM_IMAGE: //相册
                if (data == null || data.getData() == null) {
                    return;
                }
                takePhotoHelper.startPhotoZoom(data.getData(),this); //裁剪
                break;
            case TakePhotoHelper.REQUEST_CUTTING: //裁剪之后
                if(data==null){
                    return;
                }
                Bundle bundle=data.getExtras();
                if(bundle!=null){
                    Bitmap photo = bundle.getParcelable("data");
                    takePhotoHelper.saveBitmap(photo,receiveHandler);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private Handler receiveHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case TakePhotoHelper.IMAGE_SAVE_OVER://图片保存完毕
                    upDateImageView(msg);
                    //TODO 向服务器上传头像
                    personInfoPresenter.upDateHeadToServer(msg,this);
                    break;
                case PersonInfoPresenter.HEAD_UPDATE_OVER:
                    upDateImageView();
                    break;
            }
        }
    };

    /**
     * 收到Msg后更新头像
     * @param msg 保存完图片后发送来的msg
     */
    private void upDateImageView(Message msg) {
        if(msg.obj!=null&&msg.obj instanceof Uri){
            if(iv_user_avatar!=null){
                iv_user_avatar.setImageURI((Uri) msg.obj);
            }
        }
    }

    private void upDateImageView(){
        String path=ApiHttpClient.API_PIC+provider.getMineInfo(LoginManager.getInstance().getPhone(getApplication()
                ),"headsmall");
        if(iv_user_avatar!=null){
            iv_user_avatar.setImageURI(path);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PermissionHelper.CAMERA_PERMISSION: //相机权限
                if(PermissionHelper.getInstance().isPermission(permissions,grantResults,
                        new String[]{Manifest.permission.CAMERA})){
                    TakePhotoHelper takePhotoHelper=new TakePhotoHelper(getActivity());
                    takePhotoHelper.takePhotos();
                }
                break;
            case PermissionHelper.PHOTO_PERMISSION: //相册权限
                if(PermissionHelper.getInstance().isPermission(permissions,grantResults,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE})){
                    TakePhotoHelper takePhotoHelper=new TakePhotoHelper(getActivity());
                    takePhotoHelper.openSystemAlbum();
                }
                break;
            case PermissionHelper.SDCARD_PERMISSION: //sd卡权限
                if(PermissionHelper.getInstance().isPermission(permissions,grantResults,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE})){
                    SDHelper.getSDHelper().reCreateRootFolder();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Fragment销毁需要清空handler，避免内存泄露
        ActivityHelper.getInstance().clearHandlerMessage(receiveHandler);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPersonInfo();
    }
}
