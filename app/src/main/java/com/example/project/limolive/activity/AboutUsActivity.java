package com.example.project.limolive.activity;

import android.os.Bundle;
import android.view.View;

import com.example.project.limolive.R;

/**
 * 作者：黄亚菲 on 2017/2/24 14:30
 * 功能：关于我们
 */
public class AboutUsActivity extends BaseActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        loadTitle();
    }


    private void loadTitle() {
        setTitleString(getString(R.string.mine_about_us));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
}
