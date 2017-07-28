package com.example.project.limolive.tencentlive.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.tencentim.model.searchFriendBean;

import java.util.List;

/**
 * 作者：hpg on 2017/1/8 11:06
 * 功能：好友列表搜索框中的adpter
 */
public class FriendListAdapter extends BaseAdapter {
    private Context context;
    private List<searchFriendBean> searchsFriendBeens;

    public FriendListAdapter(Context context, List<searchFriendBean> searchsFriendBeens) {
        this.context = context;
        this.searchsFriendBeens = searchsFriendBeens;
    }

    @Override
    public int getCount() {
        return searchsFriendBeens.size();
    }

    @Override
    public Object getItem(int position) {
        return searchsFriendBeens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.friendlist_item, null);
            vh.iv_head_icon = (ImageView) convertView.findViewById(R.id.iv_head_icon);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_name.setText(searchsFriendBeens.get(position).getNickname());
        showHeadIcon(vh.iv_head_icon,searchsFriendBeens.get(position).getHeadsmall());
        return convertView;
    }

    private class ViewHolder {
        ImageView iv_head_icon;
        TextView tv_name;
    }

    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_avatar);
            view.setImageBitmap(bitmap);
        } else {
            RequestManager req = Glide.with(context);
            req.load(ApiHttpClient.API_PIC + avatar).into(view);
        }
    }
}
