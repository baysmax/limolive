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

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.FollowLiveBeans;
import com.example.project.limolive.bean.NewLiveBean;
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.bean.home.HomeListBeens;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentlive.model.CurLiveInfo;
import com.example.project.limolive.tencentlive.model.LiveMySelfInfo;
import com.example.project.limolive.tencentlive.utils.Constants;
import com.example.project.limolive.tencentlive.views.LiveingActivity;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.view.RoundCornersImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by AAA on 2017/8/14.
 */

public class FollowAdapter  extends RecyclerView.Adapter {
    List<HomeListBeens> followList;
    Context context;

    public FollowAdapter(List<HomeListBeens> followList, Context context) {
        this.followList = followList;
        this.context = context;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowHolder(View.inflate(context, R.layout.item_follow,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HomeListBeens homeListBeen = followList.get(position);
        final FollowHolder holder1= (FollowHolder) holder;
        holder1.iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder1.iv_follow.setEnabled(false);
                Api.followHandle(LoginManager.getInstance().getUserID(context), homeListBeen.getUid(), new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode()==Api.SUCCESS){
                            followList.remove(position);
                            notifyDataSetChanged();
                            holder1.iv_follow.setEnabled(true);
                            ToastUtils.showShort(context,"取消关注成功");
                        }
                    }
                });
            }
        });
        holder1.largeImgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(homeListBeen.getAvRoomId())){
                    ToastUtils.showShort(context,homeListBeen.getNickname()+" 未开播");
                    return;
                }
                if (homeListBeen.getHost().getUid().equals(LiveMySelfInfo.getInstance().getId())) {
                    Intent intent = new Intent(context, LiveingActivity.class);
                    intent.putExtra(Constants.ID_STATUS, Constants.HOST);
                    LiveMySelfInfo.getInstance().setIdStatus(Constants.HOST);
                    LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                    CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                    CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                    CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                    CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                    CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                    CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                    CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                    CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                    CurLiveInfo.setTitle(homeListBeen.getTitle());
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, LiveingActivity.class);
                    intent.putExtra(Constants.ID_STATUS, Constants.MEMBER);
                    LiveMySelfInfo.getInstance().setIdStatus(Constants.MEMBER);
                    LiveMySelfInfo.getInstance().setJoinRoomWay(false);
                    CurLiveInfo.setHostID(homeListBeen.getHost().getUid());
                    CurLiveInfo.setHostName(homeListBeen.getHost().getUsername());
                    CurLiveInfo.setHostAvator(homeListBeen.getHost().getAvatar());
                    CurLiveInfo.setRoomNum(homeListBeen.getAvRoomId());
                    CurLiveInfo.setHost_phone(homeListBeen.getHost().getPhone());
                    CurLiveInfo.setMembers(Integer.parseInt(homeListBeen.getWatchCount()) + 1); // 添加自己
                    CurLiveInfo.setAdmires(Integer.parseInt(homeListBeen.getAdmireCount()));
                    CurLiveInfo.setAddress(homeListBeen.getLbs().getAddress());
                    CurLiveInfo.setTitle(homeListBeen.getTitle());
                    context.startActivity(intent);
                }

                //进入直播间点击
            }
        });
        if (TextUtils.isEmpty(homeListBeen.getAvRoomId())){
            holder1.tv_nick.setText(homeListBeen.getNickname());
            holder1.num.setText("0 人观看");
            holder1.tv_mlz.setText("魅力值 "+homeListBeen.getCharm());
            holder1.isLive.setImageDrawable(context.getDrawable(R.drawable.wkb));
            if (homeListBeen.getHeadsmall().contains("http://")){
                holder1.avatar.setImageURI(homeListBeen.getHeadsmall());
                ImageLoader.getInstance().displayImage(homeListBeen.getHeadsmall(),holder1.largeImgs);
            }else {
                holder1.avatar.setImageURI(ApiHttpClient.API_PIC+homeListBeen.getHeadsmall());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+homeListBeen.getHeadsmall(),holder1.largeImgs);
            }
            return;
        }
        holder1.tv_nick.setText(homeListBeen.getHost().getUsername());
        holder1.num.setText(homeListBeen.getWatchCount()+"人观看");
        holder1.tv_mlz.setText("魅力值 "+homeListBeen.getCharm());
        Log.i("直播列表","homeListBeen="+homeListBeen.toString());
        if ((!TextUtils.isEmpty(homeListBeen.getHost().getAvatar()))&&homeListBeen.getHost().getAvatar().contains("http://")){
            holder1.avatar.setImageURI(homeListBeen.getHost().getAvatar());
        }else {
            holder1.avatar.setImageURI(ApiHttpClient.API_PIC+homeListBeen.getHost().getAvatar());
        }

        if ((!TextUtils.isEmpty(homeListBeen.getCover()))&&homeListBeen.getCover().contains("http://")){
            ImageLoader.getInstance().displayImage(homeListBeen.getCover(),holder1.largeImgs);
        }else {
            ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+homeListBeen.getCover(),holder1.largeImgs);
        }
            holder1.isLive.setImageDrawable(context.getDrawable(R.drawable.zbz));
    }

    @Override
    public int getItemCount() {
        return followList.size();
    }
    private class FollowHolder extends RecyclerView.ViewHolder{
        ImageView isLive,iv_follow;
        SimpleDraweeView avatar;
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