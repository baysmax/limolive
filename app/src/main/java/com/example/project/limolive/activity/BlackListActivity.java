package com.example.project.limolive.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.R;
import com.example.project.limolive.adapter.BlackListAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.BlackListBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：黄亚菲 on 2017/2/24 14:51
 * 功能：黑名单
 */
public class BlackListActivity extends BaseActivity{

    private Context context;
    private ListView lv_black_list;
    private List<BlackListBean> listAll;
    private BlackListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        initView();
    }

    private void initView(){
        context = this;
        loadTitle();

        lv_black_list = (ListView) findViewById(R.id.lv_black_list);
        listAll = new ArrayList<>();
        getBlackListData(LoginManager.getInstance().getUserID(context));


        lv_black_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cancelBlack(LoginManager.getInstance().getUserID(context),listAll.get(i).getPhone());
            }
        });
    }



    private void loadTitle() {
        setTitleString(getString(R.string.mine_black_list));
        setLeftImage(R.mipmap.icon_return);
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /*********
     * 黑名单列表
     */
    private void getBlackListData(String uid){

        if (NetWorkUtil.isNetworkConnected(context)){

            Api.getBlackList(uid, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    if (apiResponse.getCode() == 0){
                        ToastUtils.showShort(context,"黑名单列表请求成功");
                        List<BlackListBean> list = JSONArray.parseArray(apiResponse.getData(),BlackListBean.class);
                        if (null != list){
                            listAll.clear();
                            listAll.addAll(list);
                            adapter = new BlackListAdapter(context,listAll);
                            lv_black_list.setAdapter(adapter);
                        }
                    }else{
                        ToastUtils.showShort(context,apiResponse.getMessage());
                    }
                }
            });

        }else {
            ToastUtils.showShort(context,"网络异常，请检查您的网络~");
        }

    }

    /**********
     * 取消拉黑
     */
    private void cancelBlack(String uid,String friend_phone){

        if (NetWorkUtil.isNetworkConnected(context)){

            Api.cancelBlack(uid, friend_phone, new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    if (apiResponse.getCode() == 0){
                        ToastUtils.showShort(context,"取消成功~");
                        finish();
                    }else {
                        ToastUtils.showShort(context,apiResponse.getMessage());
                    }



                }
            });

        }else {
            ToastUtils.showShort(context,"网络异常，请检查您的网络~");
        }

    }

}
