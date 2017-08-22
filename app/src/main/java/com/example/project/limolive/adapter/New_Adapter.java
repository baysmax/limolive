package com.example.project.limolive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.taowu.RecommendBean;

import java.util.List;

/**
 * Created by AAA on 2017/8/22.
 */

public class New_Adapter extends RecyclerView.Adapter {
    private Context context;
    private List<RecommendBean> list;

    public New_Adapter(Context context, List<RecommendBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new New_Holder(View.inflate(context, R.layout.item_new_find,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder1, int position) {
        New_Holder holder= (New_Holder) holder1;
        RecommendBean recommendBean = list.get(position);
        holder.tv_prices.setText(recommendBean.getShop_price());
        holder.tv_describe.setText(recommendBean.getGoods_name());
        if (TextUtils.isEmpty(recommendBean.getOriginal_img())) {
            holder.iv_img.setImageResource(R.mipmap.goods);
        } else {
            //String[] split = rb.getGoods_content().split(";");
            Log.i("获取普通商品","getOriginal_img"+recommendBean.getOriginal_img());
            Glide.with(context).load(ApiHttpClient.API_PIC + recommendBean.getOriginal_img()).into(holder.iv_img);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class New_Holder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_describe,tv_prices;
        public New_Holder(View itemView) {
            super(itemView);
            iv_img=itemView.findViewById(R.id.iv_img);
            tv_describe=itemView.findViewById(R.id.tv_describe);
            tv_prices=itemView.findViewById(R.id.tv_prices);
        }
    }
}
