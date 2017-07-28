package com.example.project.limolive.adapter;/**
 * Created by Administrator on 2016/12/14.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.live.Be_Liveing_Bean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import static com.example.project.limolive.R.id.draweview;

/**
 * MoreLiveingAdapter
 * 作者：hpg on 2016/12/29 10:40
 */

public class MoreLiveingAdapter extends BaseAdapter {
    private Context context;
    private List<Be_Liveing_Bean> data;

    public MoreLiveingAdapter(Context context, List<Be_Liveing_Bean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.view_home_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.draweview = (SimpleDraweeView) convertView.findViewById(draweview);
            viewHolder.tv_home_grad_item = (TextView) convertView.findViewById(R.id.tv_home_grad_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            Log.i("12312312312312", data.get(position).getCover());
            if (null != data.get(position).getCover() && !" ".equals(data.get(position).getCover())) {
                viewHolder.draweview.setImageURI(ApiHttpClient.API_PIC + data.get(position).getCover());
            } else {
                viewHolder.draweview.setBackgroundResource(R.drawable.button_bg1);
            }
            viewHolder.tv_home_grad_item.setText(data.get(position).getHost().getUsername());
        }
        return convertView;
    }

    private class ViewHolder {
        SimpleDraweeView draweview;
        TextView tv_home_grad_item;
    }
}
