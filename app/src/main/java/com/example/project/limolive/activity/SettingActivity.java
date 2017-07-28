package com.example.project.limolive.activity;

import android.os.Bundle;
import android.view.View;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.SettingFragment;

/**
 * 设置
 * @author hwj on 2016/12/14.
 */

public class SettingActivity extends BaseActivity {

    private SettingFragment settingFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        loadTitle();
        settingFragment=new SettingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container,settingFragment)
                .commit();
    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_setting));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
    }
}
