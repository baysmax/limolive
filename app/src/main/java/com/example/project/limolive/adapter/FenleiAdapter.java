package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.live.AllcatgoryBean;

import java.util.List;

/**
 * 作者：hpg on 2017/3/24 16:03
 * 功能：
 */
public class FenleiAdapter extends BaseAdapter {
    private List<AllcatgoryBean> AllcatgoryBeans;
    private Context mContext;
    private int setSelectPosition;

    public FenleiAdapter(List<AllcatgoryBean> allcatgoryBeans, Context mContext) {
        AllcatgoryBeans = allcatgoryBeans;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return AllcatgoryBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return AllcatgoryBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(
                R.layout.fenlei_grid_item, null);
        final TextView tv_fenleiName = (TextView) convertView.findViewById(R.id.tv_name);
        tv_fenleiName.setText(AllcatgoryBeans.get(position).getType_name());

        if (setSelectPosition==position){
            tv_fenleiName.setSelected(true);
        }else {
            tv_fenleiName.setSelected(false);
        }

        return convertView;
    }

    public void setSelectPosition(int position){
        setSelectPosition = position;
    }
}
