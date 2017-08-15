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
import com.example.project.limolive.bean.NmztBean;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.bean.home.HostInformationBeen;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by AAA on 2017/8/15.
 */

public class NmztAdapter extends RecyclerView.Adapter {
    List<HomeListBeen> zt_list;
    Context context;

    public NmztAdapter(List<HomeListBeen> zt_list, Context context) {
        this.zt_list = zt_list;
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NmztAdapter.NmztHolder(View.inflate(context, R.layout.item_nmzt,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeListBeen homeListBeen = zt_list.get(position);
        HostInformationBeen host = homeListBeen.getHost();

        NmztHolder holder1= (NmztHolder) holder;
        holder1.tv_nick.setText(host.getUsername());
        holder1.tv_pm.setText("top"+(position+1));
        if (host.getAvatar().contains("http://")){
            ImageLoader.getInstance().displayImage(host.getAvatar(),holder1.avatar);
        }else {
            ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+host.getAvatar(),holder1.avatar);
        }

    }

    @Override
    public int getItemCount() {
        return zt_list.size();
    }
    private class NmztHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView tv_nick,tv_pm;



        public NmztHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            avatar=itemView.findViewById(R.id.zt_iv_avatar);
            tv_nick=itemView.findViewById(R.id.zt_nick);
            tv_pm=itemView.findViewById(R.id.tv_pm);
        }



    }

}
