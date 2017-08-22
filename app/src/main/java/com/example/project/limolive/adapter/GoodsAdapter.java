package com.example.project.limolive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.GoodsDetails;
import com.example.project.limolive.bean.taowu.RecommendBean;

import java.util.List;

/**
 * Created by AAA on 2017/8/22.
 */

public class GoodsAdapter extends RecyclerView.Adapter{

    private List<RecommendBean> list;
    private Context context;

    public GoodsAdapter(List<RecommendBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReHolder(View.inflate(context, R.layout.goodsmg_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, final int position) {
        final RecommendBean recommendBean = list.get(position);
        ReHolder holder= (ReHolder) holder1;
        holder.tv_price.setText(recommendBean.getShop_price());
        holder.tv_name.setText(recommendBean.getGoods_name());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(
                        context
                        , GoodsDetails.class
                )
                        .putExtra("goods_id",recommendBean.getGoods_id()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ReHolder extends RecyclerView.ViewHolder{
        TextView tv_kucun,tv_repertoryNum,tv_price,tv_name;
        ImageView iv_goods_iicon,shape,iv_add;
        RelativeLayout rl;
        public ReHolder(View itemView) {
            super(itemView);
            tv_kucun=itemView.findViewById(R.id.tv_kucun);
            tv_repertoryNum=itemView.findViewById(R.id.tv_repertoryNum);
            tv_price=itemView.findViewById(R.id.tv_price);
            iv_goods_iicon=itemView.findViewById(R.id.iv_goods_iicon);
            tv_name=itemView.findViewById(R.id.tv_name);
            shape=itemView.findViewById(R.id.shape);
            iv_add=itemView.findViewById(R.id.iv_add);
            iv_add.setVisibility(View.VISIBLE);
            tv_kucun.setVisibility(View.GONE);
            tv_repertoryNum.setVisibility(View.GONE);
            rl=itemView.findViewById(R.id.rls);
        }
    }
}
