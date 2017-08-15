package com.example.project.limolive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.NewLiveBean;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.view.RoundCornersImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by AAA on 2017/8/14.
 */

public class NewAdapter extends RecyclerView.Adapter {
    List<HomeListBeen> newList;

    Context context;

    public NewAdapter(List<HomeListBeen> newList, Context context) {
        this.newList = newList;

        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsHolder(View.inflate(context, R.layout.item_news,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeListBeen homeListBeen = newList.get(position);
        NewsHolder holder1= (NewsHolder) holder;
        holder1.tv_UserName.setText(homeListBeen.getHost().getUsername());
        if (!homeListBeen.getWatchCount().equals("null")){
            holder1.isLive.setImageDrawable(context.getDrawable(R.drawable.zbz));
        }else {
            holder1.isLive.setImageDrawable(context.getDrawable(R.drawable.wkb));
        }
        holder1.tv_dizhi.setText(homeListBeen.getLbs().getAddress());
        if (homeListBeen.getCover().contains("http://")){
            ImageLoader.getInstance().displayImage(homeListBeen.getCover(),holder1.image);
        }else {
            ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+homeListBeen.getCover(),holder1.image);
        }
        holder1.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入直播间点击事件
            }
        });


    }

    @Override
    public int getItemCount() {
        return newList.size();
    }
    private class NewsHolder extends RecyclerView.ViewHolder{
        ImageView isLive;
        RoundCornersImageView image;
        TextView tv_UserName,tv_dizhi;



        public NewsHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            isLive=itemView.findViewById(R.id.iv_isLive);
            image=itemView.findViewById(R.id.draweview);
            tv_UserName=itemView.findViewById(R.id.tv_home_name);
            tv_dizhi=itemView.findViewById(R.id.tv_home_address_items);
        }


    }


}
