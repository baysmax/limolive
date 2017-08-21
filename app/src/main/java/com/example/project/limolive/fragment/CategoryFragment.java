package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.limolive.R;

/**
 * Created by AAA on 2017/8/21.
 */

public class CategoryFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_category,inflater,container);
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
