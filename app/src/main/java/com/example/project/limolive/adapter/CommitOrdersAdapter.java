package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.bean.order.CommitOrdersBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class CommitOrdersAdapter extends BaseAdapter {
    private Context context;
    private List<CommitOrdersBean.CartList> list;
    private CommitOrdersBean.CartList c;

    public CommitOrdersAdapter(Context context, List<CommitOrdersBean.CartList> list) {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        c = list.get(i);
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.commit_order_item, null);
            vh.store = (TextView) view.findViewById(R.id.commit_item_store);
            vh.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
            vh.desc = (TextView) view.findViewById(R.id.commit_item_desc);
            vh.count = (TextView) view.findViewById(R.id.commit_item_count);
            vh.kuaidi = (TextView) view.findViewById(R.id.commit_item_kuaidi);
            vh.youhui = (TextView) view.findViewById(R.id.commit_item_youhui);
            vh.beizhu = (EditText) view.findViewById(R.id.commit_item_beizhu);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.store.setText(c.getStores_name());
        ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+c.getData().get(0).getOriginal_img(),vh.iv);
        vh.desc.setText(c.getData().get(0).getGoods_name());
        vh.count.setText("X"+c.getData().get(0).getGoods_num());
        if("0.00".equals(c.getData().get(0).getShiping_price())){
            vh.kuaidi.setText("快递 不包邮");
        }else {
            vh.kuaidi.setText("快递 不包邮");
        }
        return view;
    }

    private class ViewHolder {
        private TextView store, desc, count, kuaidi, youhui;
        private ImageView iv;
        private EditText beizhu;
    }
}
