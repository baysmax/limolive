package com.example.project.limolive.presenter;

import android.content.Context;

import com.example.project.limolive.adapter.AttentionAdapter;
import com.example.project.limolive.adapter.FansAdapter;
import com.example.project.limolive.bean.mine.FansAttention;

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

    @Override
    protected void getDate(boolean isClear) {
        super.getDate(isClear);
        //TODO 请求接口
        for(int i=0;i<10;i++){
            FansAttention bean=new FansAttention();
            if(fansAdapter!=null){
                bean.setNickname("fans"+i);
            }else if(attentionAdapter!=null){
                bean.setNickname("attention"+i);
            }
            data.add(bean);
        }
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
            fansAdapter=new FansAdapter(context,data);
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
