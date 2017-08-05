package com.example.project.limolive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.live.AllcatgoryBean;
import com.example.project.limolive.bean.phb.PHBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by AAA on 2017/8/2.
 */

public class PHBadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = 4;
    private Context context;
    private List<PHBean> phBeanList;
    private static final int TYPE_FIST=0;
    private static final int TYPE_ITEM=1;
    private static final int TYPE_DIER=2;
    private static final int TYPE_DISAN=3;

    public PHBadapter(Context context, List<PHBean> phBeanList) {
        this.context = context;
        this.phBeanList = phBeanList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_FIST:
                return new PHBHolderFist(View.inflate(context,R.layout.item1_phb,null));
        }
        return new PHBHolder(View.inflate(context,R.layout.item2_phb,null));
    }

    @Override
    public int getItemViewType(int position) {
        if (position==getItemCount()-1){
            return TYPE_FOOTER;
        }
        switch (position){
            case 0:
                return TYPE_FIST;
            case 1:
                return TYPE_DIER;
            case 2:
                return TYPE_DISAN ;
        }

        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PHBean phBean = phBeanList.get(position);

        if (getItemViewType(position)==TYPE_FIST){
            PHBHolderFist holderFist= (PHBHolderFist) holder;
            holderFist.tv_nickName.setText(phBean.getNickname());
            holderFist.tv_MLZ.setText(phBean.getLemon_coins_sum());
            //holderFist.tv_mc1.setText(phBean.getMc());
            if (phBean.getHeadsmall().contains("http://")){
                holderFist.iv_Avatar.setImageURI(phBean.getHeadsmall());
            }else {
                holderFist.iv_Avatar.setImageURI(ApiHttpClient.API_PIC+phBean.getHeadsmall());
            }
        }else {

            PHBHolder phbHolder= (PHBHolder) holder;
            phbHolder.tv_nickName1.setText(phBean.getNickname());
            phbHolder.tv_number.setText(phBean.getLemon_coins_sum());

            if (getItemViewType(position)==TYPE_DIER){
                phbHolder.tv_mc.setBackground(context.getDrawable(R.drawable.dier));
            }else if (getItemViewType(position)==TYPE_DISAN){
                phbHolder.tv_mc.setBackground(context.getDrawable(R.drawable.disan));
            }else if (getItemViewType(position)==TYPE_ITEM){
                phbHolder.tv_mc.setText("NO."+phBean.getMc());
            }

            if (TYPE_FOOTER==getItemViewType(position)){
                phbHolder.tv_nickName1.setText(phBean.getNickname());
                phbHolder.tv_number.setText(phBean.getLemon_coins_sum());
                phbHolder.tv_mc.setText("NO."+phBean.getMc());
                if (phBean.getHeadsmall().contains("http://")){
                    phbHolder.iv_Avatar1.setImageURI(phBean.getHeadsmall());
                }else {
                    phbHolder.iv_Avatar1.setImageURI(ApiHttpClient.API_PIC+phBean.getHeadsmall());
                }
                return;
            }
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
        public PHBHolderFist(View View) {
            super(View);
            tv_mc1=(TextView) View.findViewById(R.id.tv_mc1);
            iv_Avatar=(SimpleDraweeView)View.findViewById(R.id.sdv_avatar);
            tv_MLZ=(TextView) View.findViewById(R.id.tv_mlz);
            tv_nickName=(TextView) View.findViewById(R.id.tvNickname);
        }
    }

    class PHBHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView iv_Avatar1;
        TextView tv_nickName1;
        TextView tv_number;
        TextView tv_mc;
        public PHBHolder(View View) {
            super(View);
            tv_mc=(TextView) View.findViewById(R.id.tv_mc);
            iv_Avatar1=(SimpleDraweeView)View.findViewById(R.id.sdv_avatar1);
            tv_nickName1=(TextView) View.findViewById(R.id.tvNick);
            tv_number=(TextView) View.findViewById(R.id.tv_number);
        }
    }
}
