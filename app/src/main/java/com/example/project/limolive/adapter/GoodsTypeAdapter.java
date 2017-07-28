package com.example.project.limolive.adapter;/**
 * Created by Administrator on 2017/1/12.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.GoodsTypeBeen;

import java.util.List;

/**
 * GoodsTypeBeen
 * 作者：李志超 on 2017/1/12 13:48
 */

public class GoodsTypeAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsTypeBeen> data;

    public GoodsTypeAdapter(Context context, List<GoodsTypeBeen> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        if (null != data && data.size() > 0)
            return data.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != data && data.size() > 0)
            return data.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (null != data && data.size() > 0)
            return position;
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.view_home_list_pop, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_home_pop = (TextView) convertView.findViewById(R.id.tv_home_pop);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            viewHolder.tv_home_pop.setText(data.get(position).getType_name());
        }

        return convertView;
    }


    private class ViewHolder {
        TextView tv_home_pop;
    }
}
