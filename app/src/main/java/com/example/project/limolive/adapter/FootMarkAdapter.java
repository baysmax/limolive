package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.mine.FootMarkBean;

import java.util.List;

/**
 * 作者：黄亚菲 on 2017/2/23 16:28
 * 功能：浏览足迹
 */
public class FootMarkAdapter extends BaseAdapter {

    private Context context;
    private List<FootMarkBean> list;
    private LayoutInflater mInflate;

    public FootMarkAdapter(Context context, List<FootMarkBean> list) {
        this.context = context;
        this.list = list;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() == 0 ? 0:list.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = mInflate.inflate(R.layout.activity_history_item,null);
        }

        //名称
        TextView tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
        tv_product_name.setText(list.get(i).getGoods_name());

        //价格
        TextView tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
        tv_product_price.setText(list.get(i).getShop_price());


        return view;
    }
}
