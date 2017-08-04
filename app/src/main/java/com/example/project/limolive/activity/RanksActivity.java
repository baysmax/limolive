package com.example.project.limolive.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
    private TextView tvTitle,menu_return;
    private RecyclerView lv_PHB;
    private String hostid,userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranks);
        hostid = getIntent().getStringExtra("hostid");
        userid= getIntent().getStringExtra("userid");
        persenter=new PhbPersenter(this);
        initView();
        setListener();
        persenter.getDate(hostid,userid);
        persenter.setAdapter(lv_PHB);
    }

    private void setListener() {
        menu_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RanksActivity.this.finish();
            }
        });
        srl_Down.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_dark);
        srl_Down.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl_Down.setRefreshing(true);
                if (persenter.getDate(hostid,userid)){
                    if (srl_Down.isRefreshing()){
                        srl_Down.setRefreshing(false);
                    }
                }
            }
        });
    }

    private void initView() {
        srl_Down= (SwipeRefreshLayout) findViewById(R.id.srl_Down);
        lv_PHB= (RecyclerView) findViewById(R.id.lv_PHB);
        tvTitle= (TextView) findViewById(R.id.title);
        tvTitle.setText("排行榜");
        menu_return= (TextView) findViewById(R.id.menu_return);
    }
}
