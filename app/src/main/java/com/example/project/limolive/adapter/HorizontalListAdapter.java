package com.example.project.limolive.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.taowu.RecommendBean;

import java.util.List;

/**
 * 作者：hpg on 2016/12/19 13:07
 * 功能：
 */
public class HorizontalListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<RecommendBean> list;
    private RecommendBean rb;

    public HorizontalListAdapter(Context mContext, List<RecommendBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        rb = list.get(position);
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = mInflater.inflate(R.layout.findgoods, null);
            vh.image = (ImageView) convertView.findViewById(R.id.image);
            vh.title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(rb.getGoods_name());
        vh.price.setText("¥" + rb.getShop_price());
        if (TextUtils.isEmpty(rb.getOriginal_img())) {
            vh.image.setImageResource(R.mipmap.shopicon);
        } else {
            Log.i("获取爆款商品","rb.getOriginal_img()..."+rb.getOriginal_img());
            Glide.with(mContext).load(ApiHttpClient.API_PIC + rb.getOriginal_img()).into(vh.image);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView image;
        private TextView title, price;
    }
}
