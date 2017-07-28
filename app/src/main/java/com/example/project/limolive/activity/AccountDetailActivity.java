package com.example.project.limolive.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.BillDetailsAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.BillDetailsBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 账单明细
 * @author hwj on 2016/12/19.
 * @author 黄亚菲 on 2017/02/24.
 */

public class AccountDetailActivity extends BaseActivity {

    private Context context;

    private List<BillDetailsBean> listAll;

    private TextView tv_non_data;

    //账单明细列表
    private ListView lv_bill_list;

    private BillDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        initView();
    }

    private void initView() {
        context = this;
        listAll = new ArrayList<>();
        loadTitle();
        tv_non_data = (TextView) findViewById(R.id.tv_non_data);
        lv_bill_list = (ListView) findViewById(R.id.lv_bill_list);

        getBillDatailsData(LoginManager.getInstance().getUserID(context));

    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_account_detail));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountDetailActivity.this.finish();
            }
        });
    }


    /*********
     * 获取账单明细数据
     */
    private void getBillDatailsData(String uid){

        if (NetWorkUtil.isNetworkConnected(context)){

            Api.getBillDatas(uid, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    if (apiResponse.getCode() == 0){

                        ToastUtils.showShort(context,"请求成功~");

                        List<BillDetailsBean> list = JSONArray.parseArray(apiResponse.getData(),BillDetailsBean.class);

                        if (null != list){
                            tv_non_data.setVisibility(View.GONE);
                            listAll.clear();
                            listAll.addAll(list);
                            adapter = new BillDetailsAdapter(context,listAll);
                            lv_bill_list.setAdapter(adapter);

                        }else {
                            tv_non_data.setVisibility(View.VISIBLE);
                        }

                    }else {
                        ToastUtils.showShort(context,apiResponse.getMessage());
                    }


                }
            });

        }else {
            ToastUtils.showShort(context,"网络异常，请检查您的网络连接~");
        }


    }




}
