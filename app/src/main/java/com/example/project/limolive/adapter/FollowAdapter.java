package com.example.project.limolive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.FollowLiveBeans;
import com.example.project.limolive.bean.NewLiveBean;
import com.example.project.limolive.view.RoundCornersImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by AAA on 2017/8/14.
 */

public class FollowAdapter  extends RecyclerView.Adapter {
    List<FollowLiveBeans> followList;
    Context context;

    public FollowAdapter(List<FollowLiveBeans> followList, Context context) {
        this.followList = followList;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowHolder(View.inflate(context, R.layout.item_follow,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FollowLiveBeans followLiveBeans = followList.get(position);
        FollowHolder holder1= (FollowHolder) holder;
        holder1.tv_nick.setText(followLiveBeans.getNick());
        holder1.num.setText(followLiveBeans.getNumber());
        holder1.tv_mlz.setText(followLiveBeans.getMlz());

        if (followLiveBeans.getAvatar().contains("http://")){
            ImageLoader.getInstance().displayImage(followLiveBeans.getAvatar(),holder1.avatar);
        }else {
            ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+followLiveBeans.getAvatar(),holder1.avatar);
        }

        if (followLiveBeans.getLargeImgs().contains("http://")){
            ImageLoader.getInstance().displayImage(followLiveBeans.getLargeImgs(),holder1.largeImgs);
        }else {
            ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+followLiveBeans.getLargeImgs(),holder1.largeImgs);
        }
        if (followLiveBeans.isLive()){
            holder1.isLive.setImageDrawable(context.getDrawable(R.drawable.zbz));
        }else {
            holder1.isLive.setImageDrawable(context.getDrawable(R.drawable.wkb));
        }
        holder1.iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消关注接口调用
            }
        });
        holder1.largeImgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进入直播间点击
            }
        });
    }

    @Override
    public int getItemCount() {
        return followList.size();
    }
    private class FollowHolder extends RecyclerView.ViewHolder{
        ImageView isLive,iv_follow;
        ImageView avatar;
        ImageView largeImgs;
        TextView tv_nick,tv_mlz,num;



        public FollowHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            iv_follow=itemView.findViewById(R.id.iv_follow);
            isLive=itemView.findViewById(R.id.iv_isLives);
            tv_nick=itemView.findViewById(R.id.nick);
            tv_mlz=itemView.findViewById(R.id.mlz);
            num=itemView.findViewById(R.id.Num);
            avatar=itemView.findViewById(R.id.avatar);
            largeImgs=itemView.findViewById(R.id.rciv_largeImgs);
        }


    }


}