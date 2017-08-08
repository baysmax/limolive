package com.example.project.limolive.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.adapter.AttentionAdapter;
import com.example.project.limolive.adapter.FansAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.mine.FansAttention;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 粉丝 关注
 * @author hwj on 2016/12/19.
 */

public class FansAndAttention extends Presenter {

    public static final int UPDATE_OVER=1;

    private FansAdapter fansAdapter;
    private AttentionAdapter attentionAdapter;

    private List<FansAttention> data;

    public FansAndAttention(Context context) {
        super(context);
    }
    public void getFans() {
        if (NetWorkUtil.isNetworkConnected(context)) {
            Api.getFansHandle(LoginManager.getInstance().getUserID(context), String.valueOf(2), new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {
                    data.clear();
                    data.addAll(JSONArray.parseArray(apiResponse.getData(),FansAttention.class));
                    if(fansAdapter!=null){
                        fansAdapter.notifyDataSetChanged();
                    }
                    Log.i("main","getFans:"+apiResponse.toString());
                }

            });

        } else {
            ToastUtils.showShort(context, "网络异常，请检查您的网络~");
        }


    }
    public void isFollowHandle(String uid,String fid){
        Api.followHandle(uid, fid, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {

            }
        });
    }

    @Override
    protected void getDate(boolean isClear) {
        super.getDate(isClear);
        //TODO 请求接口
        getFans();
        if(fansAdapter!=null){
            fansAdapter.notifyDataSetChanged();
        }else if(attentionAdapter!=null){
            attentionAdapter.notifyDataSetChanged();
        }
        if(tellActivity!=null){
            tellActivity.presenterTakeAction(UPDATE_OVER);
        }
    }

    public FansAdapter getFansAdapter() {
        if(fansAdapter==null){
            data=new ArrayList<>();
            getFans();
            fansAdapter=new FansAdapter(context,data,this);
        }
        return fansAdapter;
    }

    public AttentionAdapter getAttentionAdapter() {
        if(attentionAdapter==null){
            data=new ArrayList<>();
            attentionAdapter=new AttentionAdapter(context,data);
        }
        return attentionAdapter;
    }


    @Override
    public void refresh() {
        super.refresh();
        getDate(true);
    }
}
