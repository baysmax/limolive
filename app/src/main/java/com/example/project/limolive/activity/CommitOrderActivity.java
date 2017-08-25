package com.example.project.limolive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.CommitOrderAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.order.CommitOrderBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.views.PayReadyActivity;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 提交订单Activity
 *
 * @author ZL on 2017/2/24
 */

public class CommitOrderActivity extends BaseActivity implements View.OnClickListener{

    private ListView commit_listView;
    private TextView name,phone,address,price;
    private RelativeLayout commit_order_rl;
    private LinearLayout commit_order_ll;
    private List<CommitOrderBean> list;
    private CommitOrderAdapter adapter;
    private String tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_order);
        tv = getIntent().getStringExtra("tv_resou_names");
        initView();
    }

    private void initView() {
        loadTitle();
        list = new ArrayList<>();
        commit_listView = (ListView) findViewById(R.id.commit_listView);
        name = (TextView) findViewById(R.id.commit_order_name);
        phone = (TextView) findViewById(R.id.commit_order_phone);
        address = (TextView) findViewById(R.id.commit_order_address);
        price = (TextView) findViewById(R.id.commit_order_price);
        commit_order_rl = (RelativeLayout) findViewById(R.id.commit_order_rl);
        commit_order_ll = (LinearLayout) findViewById(R.id.commit_order_ll);
        adapter = new CommitOrderAdapter(this,list);
        commit_listView.setAdapter(adapter);

        commit_order_rl.setOnClickListener(this);
        commit_order_ll.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDatas();
    }

    private void loadTitle() {
        setTitleString("结算中心");
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private CommitOrderBean c;
    private void getDatas(){
        /**
         * 获取订单详情
         */
        if (!NetWorkUtil.isNetworkConnected(this)) {
            ToastUtils.showShort(this, NET_UNCONNECT);
            return;
        }
        Api.getGoodsInf(LoginManager.getInstance().getUserID(this), getIntent().getStringExtra("goods_id"),getIntent().getIntExtra("num",0),tv ,new ApiResponseHandler(this) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (apiResponse.getCode() == Api.SUCCESS) {
                    c = JSON.parseObject(apiResponse.getData(), CommitOrderBean.class);
                    list.clear();
                    list.add(c);
                    name.setText(c.getAddressList().getConsignee());
                    phone.setText(c.getAddressList().getMobile());
                    address.setText("收货地址 : "+c.getAddressList().getAddress());
                    price.setText("￥"+c.getTotalPrice());
                    adapter.notifyDataSetChanged();
                } else {
                    startActivity(new Intent(CommitOrderActivity.this,ChangeInfoActivity.class).putExtra(ChangeInfoActivity.INFO_TYPE,ChangeInfoActivity.ADDRESS));
                    ToastUtils.showShort(CommitOrderActivity.this, apiResponse.getMessage());
                    finish();
                }
            }

            @Override
            public void onFailure(String errMessage) {
                ToastUtils.showShort(CommitOrderActivity.this, errMessage);
                super.onFailure(errMessage);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.commit_order_rl:
                Intent intent=new Intent(this, ChangeInfoActivity.class);
                intent.putExtra(ChangeInfoActivity.INFO_TYPE,ChangeInfoActivity.ADDRESS);
                startActivity(intent);
                break;
            case R.id.commit_order_ll:
                EditText commit_item_beizhu = (EditText) commit_listView.getChildAt(0).findViewById(R.id.commit_item_beizhu);
                if (c != null) {
                    Intent intent1 = new Intent(this, PayReadyActivity.class);
                    intent1.putExtra("cob", c);
                    intent1.putExtra("beizhu", commit_item_beizhu.getText());
                    intent1.putExtra("type", "1");
                    intent1.putExtra("tv", tv);
                    startActivity(intent1);
                }
                break;
        }
    }
}
