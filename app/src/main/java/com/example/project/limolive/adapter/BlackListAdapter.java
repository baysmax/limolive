package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.mine.BlackListBean;

import java.util.List;

/**
 * 作者：黄亚菲 on 2017/2/24 16:05
 * 功能：黑名单列表
 */
public class BlackListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater mInflate;
    private List<BlackListBean> list;

    public BlackListAdapter(Context context, List<BlackListBean> list) {
        this.context = context;
        this.list = list;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() == 0 ? 0 : list.size();
    }

    @Override
    public Object getItem(int i) {
        if (null == list)
            return 0;
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder mholder;
        mholder = new ViewHolder();

        if (null == view){
            view = mInflate.inflate(R.layout.activity_black_list_item,null);
            view.setTag(mholder);
        }else {
            mholder = (ViewHolder) view.getTag();
        }

        mholder.im_headsmall = (ImageView) view.findViewById(R.id.im_headsmall);
        mholder.tv_cancel_black = (TextView) view.findViewById(R.id.tv_cancel_black);
        mholder.tv_name = (TextView) view.findViewById(R.id.tv_name);
        mholder.tv_phone = (TextView) view.findViewById(R.id.tv_phone);

        mholder.tv_name.setText(list.get(i).getNickname());
        mholder.tv_phone.setText(list.get(i).getPhone());

        return view;
    }


    static class ViewHolder{
        ImageView im_headsmall;
        TextView tv_name,tv_phone,tv_cancel_black;

    }





}
