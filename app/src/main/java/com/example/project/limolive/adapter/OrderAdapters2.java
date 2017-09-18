package com.example.project.limolive.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.order.OrderBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class OrderAdapters2 extends BaseAdapter {
    private Context context;
    private List<OrderBean> list;

    public OrderAdapters2(Context context, List<OrderBean> list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh1=null;
        final OrderBean orderBean = list.get(i);
        if (view==null){
            vh1 = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.all_order_items, null);
            vh1.type=view.findViewById(R.id.tv_type);
            vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
            vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
            vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
            vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
            vh1.order_codes = (TextView) view.findViewById(R.id.order_codes);
            vh1.Shipping_code = (TextView) view.findViewById(R.id.Shipping_code);
            vh1.Shipping_code.setVisibility(View.GONE);
            vh1.tv_address = (TextView) view.findViewById(R.id.tv_address);
            vh1.price = (TextView) view.findViewById(R.id.price);
            vh1.tv_evaluate=view.findViewById(R.id.tv_evaluate);
            vh1.tv_evaluate.setVisibility(View.GONE);
            view.setTag(vh1);
        }else {
            vh1 = (ViewHolder) view.getTag();
        }
        String str="";
        switch (orderBean.getStatus_id()){
            case "0":
                str="未处理";
                break;
            case "1":
                str="设置退货物流";
                break;
            case "2":
                str="已完成";
                break;

        }
        if ("未处理".equals(str)){
            vh1.tv_evaluate.setText("未处理");
            vh1.tv_evaluate.setVisibility(View.VISIBLE);
            final ViewHolder finalVh = vh1;
            vh1.type.setText("卖家未处理");
        }else if ("设置退货物流".equals(str)){
            if ("0".equals(orderBean.getShipping_code())){
                vh1.tv_evaluate.setText("设置退货物流");
                vh1.tv_evaluate.setVisibility(View.VISIBLE);
                vh1.tv_address.setText("收货地址: "+orderBean.getAddress());
                vh1.type.setText("请设置物流信息");
                final ViewHolder finalVh1 = vh1;
                vh1.tv_evaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show1(orderBean,finalVh1);
                    }
                });
            }else {
                vh1.tv_evaluate.setText("退货物流已设置");
                vh1.tv_evaluate.setVisibility(View.VISIBLE);
                vh1.type.setText("退货物流已设置");
            }


        }else if ("已完成".equals(str)){
            vh1.tv_evaluate.setText(str);
            vh1.tv_evaluate.setVisibility(View.VISIBLE);
            vh1.type.setText(str);
        }
        if (!"".equals(orderBean.getShipping_code())&&!"".equals(orderBean.getShipping_name())){
            vh1.Shipping_code.setVisibility(View.VISIBLE);
            vh1.Shipping_code.setText("物流公司: "+orderBean.getShipping_name()+"\n物流单号: "+orderBean.getShipping_code());
        }
        if (!"".equals(orderBean.getAddress())){
            vh1.tv_address.setVisibility(View.VISIBLE);
            vh1.tv_address.setText("收货地址: "+orderBean.getAddress());
        }
        if (orderBean.getGoods_list()!=null&&orderBean.getGoods_list().size()>0){
            vh1.store.setText(orderBean.getStore_name());
            ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
            vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
            vh1.count.setText("X" + orderBean.getGoods_list().get(0).getGoods_num());
            vh1.price.setText("￥"+orderBean.getGoods_list().get(0).getGoods_price());
            vh1.order_codes.setText("订单编号: "+orderBean.getOrder_id());
        }
        return view;
    }

    private EditText et_size,et_num;
    private Button btn_fig;
    private Dialog dialogs;
    private void show1(final OrderBean orderBean, final ViewHolder finalVh) {
        View view = View.inflate(context, R.layout.dialogs1, null);
        et_size = view.findViewById(R.id.et_size);
        et_num = view.findViewById(R.id.et_num);
        btn_fig = view.findViewById(R.id.btn_fig);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("请输入物流信息")
                .setView(view);
        dialogs = builder.show();
        btn_fig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shipping_code=et_num.getText().toString();
                String  shipping_name=et_size.getText().toString();
                setShipping(orderBean,shipping_code,shipping_name,finalVh);
            }


        });

    }

    private void setShipping(OrderBean orderBean,String shipping_code, String shipping_name, final ViewHolder finalVh) {
        if (TextUtils.isEmpty(shipping_code)){
            ToastUtils.showShort(context,"物流单号为空");
            return;
        }
        if (TextUtils.isEmpty(shipping_name)){
            ToastUtils.showShort(context,"物流商家名称为空");
            return;
        }
        Api.order_return_add(LoginManager.getInstance().getUserID(context),orderBean.getId(), shipping_code, shipping_name, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("售后","apiResponse="+apiResponse);
                if (apiResponse.getCode()==Api.SUCCESS){
                    finalVh.type.setText("退货成功");
                    finalVh.tv_evaluate.setVisibility(View.GONE);
                    if (dialogs!=null){
                        dialogs.dismiss();
                    }
                }
            }
        });
    }

    private class ViewHolder {
        private TextView store, desc, count, price,type,tv_evaluate,order_codes;
        private ImageView iv;
        public TextView tv_address;
        public TextView Shipping_code;
    }
}
