package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.order.OrderBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<OrderBean> list;
    private OrderBean orderBean;

    public OrderAdapter(Context context, List<OrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        return Integer.parseInt(list.get(position).getOrder_status_type()) - 1;
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh1 = null;
        ViewHolder vh2 = null;
        ViewHolder vh3 = null;
        ViewHolder vh4 = null;
        ViewHolder vh5 = null;
        orderBean = list.get(i);
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case 0:
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh1.price = (TextView) view.findViewById(R.id.price);
                    view.setTag(vh1);
                    break;
                case 1:
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh1.price = (TextView) view.findViewById(R.id.price);
                    view.setTag(vh1);
                    break;
                case 2:
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh1.price = (TextView) view.findViewById(R.id.price);
                    view.setTag(vh1);
                    break;
                case 3:
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh1.price = (TextView) view.findViewById(R.id.price);
                    view.setTag(vh1);
                    break;
                case 4:
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_item, null);
                    vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh1.price = (TextView) view.findViewById(R.id.price);
                    view.setTag(vh1);
                    break;
            }

        } else {
            switch (type) {
                case 0:
                    vh1 = (ViewHolder) view.getTag();
                    break;
                case 1:
                    vh1 = (ViewHolder) view.getTag();
                    break;
                case 2:
                    vh1 = (ViewHolder) view.getTag();
                    break;
                case 3:
                    vh1 = (ViewHolder) view.getTag();
                    break;
                case 4:
                    vh1 = (ViewHolder) view.getTag();
                    break;

            }

        }

        switch (type){
            case 0:
                vh1.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                vh1.price.setText("共"+orderBean.getGoods_list().get(0).getGoods_num()+"件  合计￥"+orderBean.getTotal_amount());
                break;
            case 1:
                vh1.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 2:
                vh1.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 3:
                vh1.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
            case 4:
                vh1.store.setText(orderBean.getStore_name());
                ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                break;
        }


        return view;
    }

    private class ViewHolder {
        private TextView store, desc, count, price;
        private ImageView iv;
    }
}
