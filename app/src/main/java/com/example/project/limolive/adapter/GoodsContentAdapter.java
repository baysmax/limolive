package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * @author hwj on 2016/12/29.
 */

public class GoodsContentAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;

    public GoodsContentAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView==null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.goods_content_item,null);
            vh.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+list.get(position),vh.image);

        return convertView;
    }

    private class ViewHolder{
        private ImageView image;
    }
}
