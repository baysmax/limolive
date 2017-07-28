package com.example.project.limolive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.bean.mine.BillDetailsBean;

import java.util.List;

/**
 * 作者：黄亚菲 on 2017/2/24 15:28
 * 功能：账单明细  数据适配器
 */
public class BillDetailsAdapter extends BaseAdapter {


    private Context context;
    private List<BillDetailsBean> list;
    private LayoutInflater mInflate;


    public BillDetailsAdapter(Context context, List<BillDetailsBean> list) {
        this.context = context;
        this.list = list;
        mInflate = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size() == 0 ?0 : list.size();
    }

    @Override
    public Object getItem(int i) {

        if (null != list)
            return  0;
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = mInflate.inflate(R.layout.activity_account_detail_item,null);
        }

        TextView tv_pay_name = (TextView) view.findViewById(R.id.tv_pay_name);
        TextView tv_pay_time = (TextView) view.findViewById(R.id.tv_pay_time);
        TextView tv_total_amount = (TextView) view.findViewById(R.id.tv_total_amount);

        tv_pay_name.setText(list.get(i).getPay_name());
        tv_pay_time.setText(list.get(i).getPay_time());
        tv_total_amount.setText(list.get(i).getTotal_amount());

        return view;
    }
}
