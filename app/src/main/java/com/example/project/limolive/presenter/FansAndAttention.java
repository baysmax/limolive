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

    private List<FansAttention> dataAttent;
    private List<FansAttention> dataFans;

    public FansAndAttention(Context context) {
        super(context);
    }
    public void getFans() {
        if (dataFans!=null){
            dataFans.clear();
        }else {
            dataFans=new ArrayList<>();
        }
        if (NetWorkUtil.isNetworkConnected(context)) {
            Api.getFansHandle(LoginManager.getInstance().getUserID(context), String.valueOf(2), new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    dataFans.addAll(JSONArray.parseArray(apiResponse.getData(),FansAttention.class));
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
        getAttention();
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
            if (dataFans==null){
                dataFans=new ArrayList<>();
            }
            getFans();
            fansAdapter=new FansAdapter(context,dataFans,this);
        }
        return fansAdapter;
    }

    public AttentionAdapter getAttentionAdapter() {
        if(attentionAdapter==null){
            dataAttent=new ArrayList<>();
            getAttention();
            attentionAdapter=new AttentionAdapter(context,dataAttent,this);
        }
        return attentionAdapter;
    }

    public void getAttention() {
        if (dataAttent!=null){
            dataAttent.clear();
        }else {
            dataAttent=new ArrayList<>();
        }
        if (NetWorkUtil.isNetworkConnected(context)) {
            Api.getFansHandle(LoginManager.getInstance().getUserID(context), String.valueOf(1), new ApiResponseHandler(context) {
                @Override
                public void onSuccess(ApiResponse apiResponse) {

                    dataAttent.addAll(JSONArray.parseArray(apiResponse.getData(),FansAttention.class));
                    if(attentionAdapter!=null){
                        attentionAdapter.notifyDataSetChanged();
                    }
                    Log.i("main","getAttention:"+apiResponse.toString());
                }

            });

        } else {
            ToastUtils.showShort(context, "网络异常，请检查您的网络~");
        }
    }


    @Override
    public void refresh() {
        super.refresh();
        getDate(true);
    }
}
