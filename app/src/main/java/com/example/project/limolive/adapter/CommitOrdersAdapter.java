package com.example.project.limolive.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class CommitOrdersAdapter extends BaseAdapter {
    private Context context;
    private List<CommitOrdersBean.CartList> list;
    private CommitOrdersBean.CartList c;
    List<CommitOrdersBean.CartList.Datas> dateList;

    public HashMap<String, String> getBeizhu() {
        return beizhu;
    }

    private HashMap<String,String> beizhu;

    public CommitOrdersAdapter(Context context, List<CommitOrdersBean.CartList> list,List<CommitOrdersBean.CartList.Datas> dateList) {
        this.context = context;
        this.list = list;
        this.dateList=dateList;
        beizhu=new HashMap<>();
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public Object getItem(int i) {
        return dateList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh = null;
        c = list.get(0);
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
            vh.beizhu.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    beizhu.put(dateList.get(i).getGoods_id(),editable.toString());
                }
            });
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.store.setText(c.getStores_name());
        ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC+dateList.get(i).getOriginal_img(),vh.iv);
        vh.desc.setText(dateList.get(i).getGoods_name());
        vh.count.setText("X"+dateList.get(i).getGoods_num());
        if("0.00".equals(dateList.get(i).getShiping_price())){
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
