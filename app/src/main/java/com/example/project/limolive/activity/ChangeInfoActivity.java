package com.example.project.limolive.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.AddressManagerFragment;
import com.example.project.limolive.fragment.BaseFragment;
import com.example.project.limolive.fragment.ChangeSimpleInfoFragment;

/**
 * 修改信息
 * <p>查看地址页面，需要传递INFO_TYPE- ADDRESS，进入修改普通信息则需传INFO_TYPE-SIMPLE，
 * ChangeSimpleInfoFragment.SIMPLE_INFO_TEXT-ChangeSimpleInfoFragment.SIMPLE_INFO_JOB/UserName</p>
 * @author hwj on 2016/12/17.
 */

public class ChangeInfoActivity extends BaseActivity {

    /**
     * INFO_TYPE，来界定本界面的显示
     */
    public static final int SIMPLE=0;
    public static final int ADDRESS=1;
    public static final int CART=3;
    public static final String INFO_TYPE="info_type";

    private BaseFragment showFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        getFragment();
        initView();
    }

    private void getFragment() {
        int infoType=getIntent().getIntExtra(INFO_TYPE, SIMPLE);
        switch(infoType){
            case SIMPLE:
                showFragment=new ChangeSimpleInfoFragment();
                break;
            case ADDRESS:
                showFragment=new AddressManagerFragment();
                break;
            default:
                finish();
        }
    }

    private void initView() {
        if(showFragment instanceof ChangeSimpleInfoFragment){
            int simple_type=getIntent().getIntExtra(ChangeSimpleInfoFragment.SIMPLE_INFO_TEXT
                    ,ChangeSimpleInfoFragment.SIMPLE_INFO_JOB);
            Bundle bundle=new Bundle();
            bundle.putInt(ChangeSimpleInfoFragment.SIMPLE_INFO_TEXT,simple_type);
            showFragment.setArguments(bundle);
        }
        bindFragment();
    }

    private void bindFragment() {
        if(showFragment!=null){
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container,showFragment);
            transaction.commit();
        }
    }
}
