package com.example.project.limolive.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.AddChangeAddressFragment;

/**
 * 添加修改地址
 * @author hwj on 2016/12/20.
 */

public class AddChangeAddressActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);
        initView();
    }

    private void initView() {
        AddChangeAddressFragment fragment=new AddChangeAddressFragment();
        Parcelable bean=getIntent().getParcelableExtra("address_model");
        if(bean!=null){
            Bundle bundle=new Bundle();
            bundle.putParcelable("address_model",bean);
            fragment.setArguments(bundle);
        }
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,fragment);
        transaction.commit();
    }

}
