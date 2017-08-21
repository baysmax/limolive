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
import com.example.project.limolive.bean.home.HomeListBeen;
import com.example.project.limolive.view.RoundCornersImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import static com.example.project.limolive.R.id.draweview;

/**
 * HomeAdapter
 * 作者：李志超 on 2016/12/14 16:40
 */

public class HomeAdapter extends BaseAdapter {
    private Context context;
    private List<HomeListBeen> data;

    public HomeAdapter(Context context, List<HomeListBeen> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.view_home_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.draweview = (RoundCornersImageView) convertView.findViewById(draweview);
            viewHolder.tv_home_grad_item = (TextView) convertView.findViewById(R.id.tv_home_grad_item);
            viewHolder.tv_numbers = (TextView) convertView.findViewById(R.id.tv_numbers);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (data != null) {
            Log.i("12312312312312", data.get(position).getCover());
            if (null != data.get(position).getCover() && !" ".equals(data.get(position).getCover())) {
                if (data.get(position).getCover().contains("http")){
                    ImageLoader.getInstance().displayImage(data.get(position).getCover(),viewHolder.draweview);
                }else {
                    ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + data.get(position).getCover(),viewHolder.draweview);
                }
            } else {
                viewHolder.draweview.setBackgroundResource(R.mipmap.head2);
            }
            viewHolder.tv_home_grad_item.setText(data.get(position).getTitle());
            viewHolder.tv_numbers.setText(data.get(position).getWatchCount());
        }

        return convertView;
    }

    private class ViewHolder {
        RoundCornersImageView draweview;
        TextView tv_home_grad_item;
        TextView tv_numbers;
    }

}
