package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.limolive.R;

/**
 * 提交订单
 * @author hwj on 2016/12/23.
 */

public class CommitOrderFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_order_commit,inflater, container);
    }
}
