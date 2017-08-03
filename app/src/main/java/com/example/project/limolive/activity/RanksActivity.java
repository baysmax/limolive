package com.example.project.limolive.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.project.limolive.R;
import com.example.project.limolive.adapter.PHBadapter;
import com.example.project.limolive.bean.phb.PHBean;
import com.example.project.limolive.presenter.PhbPersenter;
import com.example.project.limolive.presenter.Presenter;

import java.util.ArrayList;

/**
 * Created by AAA on 2017/8/1.
 */

public class RanksActivity extends BaseActivity{
    private SwipeRefreshLayout srl_Down;

    private PhbPersenter persenter;

    private RecyclerView lv_PHB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranks);
        String hostid = getIntent().getStringExtra("hostid");
        String userid = getIntent().getStringExtra("userid");
        persenter=new PhbPersenter(this);
        lv_PHB= (RecyclerView) findViewById(R.id.lv_PHB);
        persenter.getDate(hostid,userid);
        persenter.setAdapter(lv_PHB);
    }
}
