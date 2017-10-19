package com.example.project.limolive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.LiveRechargeBean;
import com.example.project.limolive.utils.ImageUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by AAA on 2017/9/27.
 */

public class LiveRankConsumptionAdapter extends RecyclerView.Adapter {
    private List<LiveRechargeBean> liveRankList;
    private Context context;

    public LiveRankConsumptionAdapter(List<LiveRechargeBean> liveRankList, Context context) {
        this.liveRankList = liveRankList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RankHolder(View.inflate(context, R.layout.item_live_rank,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, int position) {
        LiveRechargeBean liveRechargeBean = liveRankList.get(position);
        RankHolder holder= (RankHolder) holders;
        holder.tv_user_nick.setText(liveRechargeBean.getNickname());
        holder.tv_price.setText("消费值 "+liveRechargeBean.getOrder_price());
        if (liveRechargeBean.getHeadsmall().contains("http://")){
            holder.iv_avatar.setImageURI(liveRechargeBean.getHeadsmall(), ImageUtils.getOptions());
        }else {
            holder.iv_avatar.setImageURI(ApiHttpClient.API_PIC+liveRechargeBean.getHeadsmall(),ImageUtils.getOptions());
        }
        switch (position){
            case 0:
                holder.tv_number.setBackground(context.getDrawable(R.drawable.diyi));
                holder.tv_number.setText("");
                break;
            case 1:
                holder.tv_number.setBackground(context.getDrawable(R.drawable.dier));
                holder.tv_number.setText("");
                break;
            case 2:
                holder.tv_number.setBackground(context.getDrawable(R.drawable.disan));
                holder.tv_number.setText("");
                break;
            default:
                holder.tv_number.setText("NO."+(position+1));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return liveRankList.size();
    }

    class RankHolder extends RecyclerView.ViewHolder{
        TextView tv_number,tv_user_nick,tv_price;
        SimpleDraweeView iv_avatar;
        public RankHolder(View itemView) {
            super(itemView);
            tv_number=itemView.findViewById(R.id.tv_number);
            tv_user_nick=itemView.findViewById(R.id.tv_user_nick);
            tv_price=itemView.findViewById(R.id.tv_price);
            iv_avatar=itemView.findViewById(R.id.iv_avatar);
        }
    }
}
