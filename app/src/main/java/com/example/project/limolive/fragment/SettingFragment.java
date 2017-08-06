package com.example.project.limolive.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.AboutUsActivity;
import com.example.project.limolive.activity.BlackListActivity;
import com.example.project.limolive.activity.LoginActivity;
import com.example.project.limolive.activity.PersonInfoActivity;
import com.example.project.limolive.activity.UserRebackActivity;
import com.example.project.limolive.activity.bindPhoneActivity;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.provider.MineDataProvider;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.CustomDialog;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设置
 *
 * @author hwj on 2016/12/14.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private CustomDialog.Builder builder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PgyCrashManager.register(getActivity());
        PgyUpdateManager.register(getActivity(),getString(R.string.file_provider));
        return setContentView(R.layout.fragment_setting, inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.rl_person_info).setOnClickListener(this);
        findViewById(R.id.rl_number_safe).setOnClickListener(this);
        findViewById(R.id.rl_push_setting).setOnClickListener(this);
        findViewById(R.id.rl_invite_friend).setOnClickListener(this);
        findViewById(R.id.rl_clear_junk).setOnClickListener(this);
        findViewById(R.id.rl_user_return).setOnClickListener(this);
        findViewById(R.id.rl_about_us).setOnClickListener(this);
        findViewById(R.id.rl_black_list).setOnClickListener(this);
        findViewById(R.id.tv_login_out).setOnClickListener(this);
        findViewById(R.id.rl_check).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_person_info: //个人资料
                Intent personInfo = new Intent(getActivity(), PersonInfoActivity.class);
                startActivity(personInfo);
                break;
            case R.id.rl_number_safe://账户安全
                Intent intent = new Intent(getActivity(), bindPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_push_setting://推送设置

                break;
            case R.id.rl_invite_friend: //邀请好友
                share();
                break;
            case R.id.rl_clear_junk://清理缓存
                clearCache();
                break;
            case R.id.rl_user_return://用户反馈
                Intent rebackIntent = new Intent(getActivity(), UserRebackActivity.class);
                startActivity(rebackIntent);
                break;
            case R.id.rl_about_us://关于我们
                Intent intentAbout = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intentAbout);
                break;
            case R.id.rl_black_list://黑名单
                Intent intentBlackList = new Intent(getActivity(), BlackListActivity.class);
                startActivity(intentBlackList);
                break;
            case R.id.tv_login_out://退出登录
                loginOut();
                break;
            case R.id.rl_check://检查更新

                // 版本检测方式2：带更新回调监听
                PgyUpdateManager.register(getActivity(),getString(R.string.file_provider),
                        new UpdateManagerListener() {
                            @Override
                            public void onUpdateAvailable(final String result) {

                                new AlertDialog.Builder(getActivity())
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
                                                                        getActivity(),
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
                                ToastUtils.showCustom(getActivity(), "已经是最新版本", Toast.LENGTH_SHORT);
                            }
                        });
                break;
        }
    }

    @Override
    public void onDestroy() {
        PgyCrashManager.unregister();
        super.onDestroy();
    }

    /**
     * 分享
     */
    private void share() {
        UMImage thumb = new UMImage(getActivity(), R.mipmap.logo);
        UMWeb web = new UMWeb("https://www.pgyer.com/ae5b99158329df67baa9b83df1093b75");
        web.setTitle("柠檬直播");//标题
        web.setThumb(thumb);  //缩略图
        web.setDescription("大家好,我正在直播哦，喜欢我的朋友赶紧来哦");//描述
        new ShareAction(getActivity())
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

            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /******
     * 清除缓存
     */
    private void clearCache() {
        builder = new CustomDialog.Builder(getActivity());
        builder.setMessage("是否清除缓存");
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("清除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ToastUtils.showShort(getActivity(), "已瘦身");
            }
        });
        builder.create().show();
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
}
