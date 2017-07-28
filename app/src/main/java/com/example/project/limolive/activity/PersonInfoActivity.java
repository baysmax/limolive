package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.PersonInfoFragment;

/**
 * 个人资料
 * 所有的子页面均用fragment展示，容器为 fragment_container
 *
 * @author hwj on 2016/12/15.
 */

public class PersonInfoActivity extends BaseActivity {

    private PersonInfoFragment personInfoFragment; //个人信息主页面

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initView();
    }


    private void initView() {
        loadTitle();
        initFragment();
    }

    private void initFragment() {
        personInfoFragment = new PersonInfoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, personInfoFragment).commit();
    }


    private void loadTitle() {
        setTitleString(getString(R.string.mine_person_info));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonInfoActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
