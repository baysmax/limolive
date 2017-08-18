package com.example.project.limolive.sidebar;

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
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.tencentim.model.FriendInfoBean;
import com.example.project.limolive.tencentim.widget.CircleImageView;

import java.util.List;


/**
 * 作用: 字母排序列表的适配器
 *
 * @author LITP
 * @date 2016/11/4
 */

public class BMJLetterListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<?> mDatas;
    private Context context;

    public BMJLetterListAdapter(Context context, List<?> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.e("tpnet", "总数" + mDatas.size());
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        MyViewHolder myViewHolder = null;

        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = mInflater.inflate(R.layout.item_bmj_letter_list, arg2, false);
            myViewHolder.letterText = (TextView) convertView.findViewById(R.id.bmj_tv_letter);
            myViewHolder.contentText = (TextView) convertView.findViewById(R.id.bmj_tv_content);
            myViewHolder.iv_head_icon = (CircleImageView) convertView.findViewById(R.id.iv_head_icon);

            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        // DiseaseList item = (DiseaseList) getItem(position);

        FriendInfoBean item = (FriendInfoBean) getItem(position);
        if (item != null) {
            //如果有字母标识就显示 字母那一行
            if (!item.getLetter().isEmpty()) {
                myViewHolder.letterText.setText(item.getLetter());
                myViewHolder.letterText.setVisibility(View.VISIBLE);
            } else { //否则隐藏
                myViewHolder.letterText.setVisibility(View.GONE);
            }

            // myViewHolder.contentText.setText(item.getXmlName());
            myViewHolder.contentText.setText(item.getNickname());
            showHeadIcon(myViewHolder.iv_head_icon, item.getHeadsmall());
        }

        return convertView;
    }

    private class MyViewHolder {
        private TextView letterText;
        private CircleImageView iv_head_icon;
        private TextView contentText;

    }

    private void showHeadIcon(ImageView view, String avatar) {
        if (TextUtils.isEmpty(avatar)) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_login_head);
            view.setImageBitmap(bitmap);
        } else {
            RequestManager req = Glide.with(context);
            if (avatar.contains("http://")) {
                req.load(avatar).into(view);
            } else {
                req.load(ApiHttpClient.API_PIC + avatar).into(view);
            }
        }
    }
}
