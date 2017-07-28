package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.mine.MyCollectBean;

import java.util.List;

/**
 * 作者：黄亚菲 on 2017/2/23 15:23
 * 功能：我的收藏列表
 */
public class MyCollectAdapter extends BaseAdapter {


    private Context context;
    private List<MyCollectBean> list;
    private LayoutInflater mInflate;

    public MyCollectAdapter(Context context, List<MyCollectBean> list) {
        this.context = context;
        this.list = list;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() == 0 ?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        if (list == null)
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
            view = mInflate.inflate(R.layout.activity_my_collect_item,null);
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
