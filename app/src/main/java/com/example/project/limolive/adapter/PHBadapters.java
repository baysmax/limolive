package com.example.project.limolive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.phb.IsFollow;
import com.example.project.limolive.bean.phb.PHBean;
import com.example.project.limolive.bean.phb.PHBeans;
import com.example.project.limolive.helper.LoginManager;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by AAA on 2017/8/2.
 */

public class PHBadapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private static final int TYPE_FOOTER = 4;
    private Context context;
    private List<PHBeans> phBeanList;
    private static final int TYPE_FIST=0;
    private static final int TYPE_ITEM=1;
    private static final int TYPE_DIER=2;
    private static final int TYPE_DISAN=3;

    public PHBadapters(Context context, List<PHBeans> phBeanList) {
        this.context = context;
        this.phBeanList = phBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_FIST:
                return new PHBHolderFist(View.inflate(context,R.layout.item1_phbs,null));
        }
        return new PHBHolder(View.inflate(context,R.layout.item2_phbs,null));
    }

    @Override
    public int getItemViewType(int position) {
//        if (position==getItemCount()-1){
//            return TYPE_FOOTER;
//        }
        switch (position){
            case 0:
                return TYPE_FIST;
            case 1:
                return TYPE_DIER;
            case 2:
                return TYPE_DISAN;
        }

        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PHBeans phBean = phBeanList.get(position);

        if (getItemViewType(position)==TYPE_FIST){
            final PHBHolderFist holderFist= (PHBHolderFist) holder;
            holderFist.tv_nickName.setText(phBean.getNickname());
            holderFist.tv_MLZ.setText(phBean.getCharm());
            //holderFist.tv_mc1.setText(phBean.getMc());
            if (phBean.getFollow().equals("1")){
                holderFist.btn_is_follow.setBackground(context.getDrawable(R.drawable.follow));
            }else {
                holderFist.btn_is_follow.setBackground(context.getDrawable(R.drawable.wfollow));
            }
            if (phBean.getHeadsmall().contains("http://")){
                holderFist.iv_Avatar.setImageURI(phBean.getHeadsmall());
            }else {
                holderFist.iv_Avatar.setImageURI(ApiHttpClient.API_PIC+phBean.getHeadsmall());
            }
            holderFist.btn_is_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Api.followHandle(LoginManager.getInstance().getUserID(context), phBean.getUid(), new ApiResponseHandler(context) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            Log.i("关注","getCode="+apiResponse.getCode()+",getData="+apiResponse.getData().toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                holderFist.btn_is_follow.setBackground(context.getDrawable(R.drawable.follow));
                            }else if (apiResponse.getCode()==Api.CANCLE){
                                holderFist.btn_is_follow.setBackground(context.getDrawable(R.drawable.wfollow));
                            }
                        }
                    });
                }
            });
        }else {

            final PHBHolder phbHolder= (PHBHolder) holder;
            phbHolder.tv_nickName1.setText(phBean.getNickname());
            phbHolder.tv_number.setText(phBean.getCharm());

            if (getItemViewType(position)==TYPE_DIER){
                phbHolder.tv_mc.setBackground(context.getDrawable(R.drawable.dier));
            }else if (getItemViewType(position)==TYPE_DISAN){
                phbHolder.tv_mc.setBackground(context.getDrawable(R.drawable.disan));
            }else if (getItemViewType(position)==TYPE_ITEM){
                phbHolder.tv_mc.setText("NO."+(position+1));
            }


            if (phBean.getFollow().equals("1")){
                phbHolder.btn_is_follow.setBackground(context.getDrawable(R.drawable.follow));
            }else {
                phbHolder.btn_is_follow.setBackground(context.getDrawable(R.drawable.wfollow));
            }
            phbHolder.btn_is_follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Api.followHandle(LoginManager.getInstance().getUserID(context), phBean.getUid(), new ApiResponseHandler(context) {
                        @Override
                        public void onSuccess(ApiResponse apiResponse) {
                            Log.i("关注","getCode="+apiResponse.getCode()+",getData="+apiResponse.getData().toString());
                            if (apiResponse.getCode()==Api.SUCCESS){
                                phbHolder.btn_is_follow.setBackground(context.getDrawable(R.drawable.follow));
                            }else if (apiResponse.getCode()==Api.CANCLE){
                                phbHolder.btn_is_follow.setBackground(context.getDrawable(R.drawable.wfollow));
                            }
                        }
                    });
                }
            });

//            if (TYPE_FOOTER==getItemViewType(position)){
//                phbHolder.tv_nickName1.setText(phBean.getNickname());
//                phbHolder.tv_number.setText(phBean.getLemon_coins_sum());
//                phbHolder.tv_mc.setText("NO."+phBean.getMc());
//                if (phBean.getHeadsmall().contains("http://")){
//                    phbHolder.iv_Avatar1.setImageURI(phBean.getHeadsmall());
//                }else {
//                    phbHolder.iv_Avatar1.setImageURI(ApiHttpClient.API_PIC+phBean.getHeadsmall());
//                }
//                return;
//            }
            if (phBean.getHeadsmall().contains("http://")){
                phbHolder.iv_Avatar1.setImageURI(phBean.getHeadsmall());
            }else {
                phbHolder.iv_Avatar1.setImageURI(ApiHttpClient.API_PIC+phBean.getHeadsmall());
            }
        }

    }
    @Override
    public int getItemCount() {
        return phBeanList.size();
    }

    class PHBHolderFist extends RecyclerView.ViewHolder{
        SimpleDraweeView iv_Avatar;
        TextView tv_nickName;
        TextView tv_MLZ;
        TextView tv_mc1;
        Button btn_is_follow;
        public PHBHolderFist(View View) {
            super(View);
            tv_mc1=(TextView) View.findViewById(R.id.tv_mc1);
            iv_Avatar=(SimpleDraweeView)View.findViewById(R.id.sdv_avatar);
            tv_MLZ=(TextView) View.findViewById(R.id.tv_mlz);
            tv_nickName=(TextView) View.findViewById(R.id.tvNickname);
            btn_is_follow=View.findViewById(R.id.btn_is_follow);
        }
    }

    class PHBHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView iv_Avatar1;
        TextView tv_nickName1;
        TextView tv_number;
        TextView tv_mc;
        Button btn_is_follow;
        public PHBHolder(View View) {
            super(View);
            tv_mc=(TextView) View.findViewById(R.id.tv_mc);
            iv_Avatar1=(SimpleDraweeView)View.findViewById(R.id.sdv_avatar1);
            tv_nickName1=(TextView) View.findViewById(R.id.tvNick);
            tv_number=(TextView) View.findViewById(R.id.tv_number);
            btn_is_follow=View.findViewById(R.id.btn_is_follow);
        }
    }
}
