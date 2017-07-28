package com.example.project.limolive.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.project.limolive.R;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

/**
 * 作者：hpg on 2017/4/7 19:10
 * 功能：
 */
public class CheckUpdataActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PgyCrashManager.register(this);

        PgyUpdateManager.register(CheckUpdataActivity.this,"",
        new UpdateManagerListener() {

            @Override
            public void onUpdateAvailable(final String result) {

                // 将新版本信息封装到AppBean中
                final AppBean appBean = getAppBeanFromString(result);
                new AlertDialog.Builder(CheckUpdataActivity.this)
                        .setTitle("更新")
                        .setMessage("主人有新的版本更新哟...")
                        .setNegativeButton(
                                "确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        startDownloadTask(
                                                CheckUpdataActivity.this,
                                                appBean.getDownloadURL());
                                    }
                                }).show();
            }

            @Override
            public void onNoUpdateAvailable() {
            }
        });
    }
}
