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
 * 作者：黄亚菲 on 2017/2/21 14:45
 * 功能：商品分类列表
 *
 * CommonAdapter<ProductSortBean>
 */
public class ProductSortAdapter extends BaseAdapter{

    private List<AllcatgoryBean> list;
    private Context context;
    private LayoutInflater mInflate;


    public ProductSortAdapter(List<AllcatgoryBean> list,Context context) {
        this.list = list;
        this.context = context;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (null == list)
            return 0;
        return list.size();
    }

    @Override
    public Object getItem(int i) {

        if (list.size() == 0)
            return 0;
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = mInflate.inflate(R.layout.activity_product_sort_item,null);
        }
        TextView tv = (TextView) view.findViewById(R.id.tv_sort_name);
        tv.setText(list.get(i).getType_name());
        return view;
    }

}
