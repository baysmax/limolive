package com.example.project.limolive.tencentim.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.tencentim.model.searchFriendBean;
import com.example.project.limolive.tencentlive.utils.GlideCircleTransform;
import com.example.project.limolive.tencentlive.utils.UIUtils;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;

import java.util.List;

import static com.example.project.limolive.presenter.Presenter.NET_UNCONNECT;

/**
 * 作者：hpg on 2017/1/6 11:14
 * 功能：
 */
public class searchNewFriendAdapter extends BaseAdapter {

    private Context context;
    private List<searchFriendBean> searchFriendBeens;

    public searchNewFriendAdapter(Context context, List<searchFriendBean> searchFriendBeens) {
        this.context = context;
        this.searchFriendBeens = searchFriendBeens;
    }

    @Override
    public int getCount() {
        return searchFriendBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return searchFriendBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.searchfriend_item, null);
            vh.iv_add_friend = (ImageView) convertView.findViewById(R.id.iv_add_friend);
            vh.iv_head_icon = (ImageView) convertView.findViewById(R.id.iv_head_icon);
            vh.tv_describe = (TextView) convertView.findViewById(R.id.tv_describe);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText(searchFriendBeens.get(position).getNickname());
        vh.tv_describe.setText(searchFriendBeens.get(position).getPersonalized());
        showHeadIcon(vh.iv_head_icon,searchFriendBeens.get(position).getHeadsmall());

        final ViewHolder finalVh = vh;
        //先判断是不是好友
        if(searchFriendBeens.get(position).getIs_friend().equals("0")){
            finalVh.iv_add_friend.setImageResource(R.mipmap.addfriend);
            notifyDataSetChanged();
            vh.iv_add_friend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addfriend(searchFriendBeens.get(position).getPhone(),finalVh.iv_add_friend);
                }
            });
        }else {
            finalVh.iv_add_friend.setImageResource(R.mipmap.added);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_add_friend;
        ImageView iv_head_icon;
        TextView tv_describe;
        TextView tv_name;
    }
    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avatar);
            Bitmap cirBitMap = UIUtils.createCircleImage(bitmap, 0);
            view.setImageBitmap(cirBitMap);
        } else if(avatar.contains("http://")) {
            RequestManager req = Glide.with(context);
            req.load(avatar).transform(new GlideCircleTransform(context)).into(view);
        }else {
            RequestManager req = Glide.with(context);
            req.load(ApiHttpClient.API_PIC + avatar).transform(new GlideCircleTransform(context)).into(view);
        }
    }
    /**
     * 加好友
     */
    private void addfriend(String friend_phone, final ImageView iv) {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        Api.addfriend(LoginManager.getInstance().getUserID(context), friend_phone, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("加好友", apiResponse.toString());
                ToastUtils.showShort(context, apiResponse.getMessage());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    iv.setImageResource(R.mipmap.added);
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
            }
        });
    }
}
