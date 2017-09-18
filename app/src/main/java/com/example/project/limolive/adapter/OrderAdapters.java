package com.example.project.limolive.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.CommitOrderActivity;
import com.example.project.limolive.activity.ProductDescriptionActivity;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiHttpClient;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.GoodsStandard;
import com.example.project.limolive.bean.order.OrderBean;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class OrderAdapters extends BaseAdapter {
    private Context context;
    private List<OrderBean> list;
    private String type="";
    AlertDialog dialog = null;

    public OrderAdapters(Context context, List<OrderBean> list, String type) {
        this.context = context;
        this.list = list;
        this.type=type;
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
        if ("".equals(type)){
            return 0;//全部
        }else if ("WAITPAY".equals(type)){
            return 1;//待发货
        }else if ("WAITSEND".equals(type)){
            return 2;//已完成
        }
       return 0;
    }
    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh1 = null;
        ViewHolder vh2 = null;
        ViewHolder vh3 = null;

        final OrderBean orderBean = list.get(i);
        int type = getItemViewType(i);
        if (view == null) {
            switch (type) {
                case 0://全部
                    vh1 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_items, null);
                    vh1.type=view.findViewById(R.id.tv_type);
                    vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh1.price = (TextView) view.findViewById(R.id.price);
                    vh1.order_codes = (TextView) view.findViewById(R.id.order_codes);
                    vh1.tv_address = (TextView) view.findViewById(R.id.tv_address);
                    vh1.tv_address.setVisibility(View.GONE);
                    vh1.Shipping_code=view.findViewById(R.id.Shipping_code);
                    vh1.Shipping_code.setVisibility(View.GONE);
                    vh1.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh1.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh1);
                    break;
                case 1://待发货
                    vh2 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_items, null);
                    vh2.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh2.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh2.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh2.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh2.price = (TextView) view.findViewById(R.id.price);
                    vh2.order_codes = (TextView) view.findViewById(R.id.order_codes);
                    vh2.tv_address = (TextView) view.findViewById(R.id.tv_address);
                    vh2.tv_address.setVisibility(View.GONE);
                    vh2.Shipping_code=view.findViewById(R.id.Shipping_code);
                    vh2.Shipping_code.setVisibility(View.GONE);
                    vh2.type=view.findViewById(R.id.tv_type);
                    vh2.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh2.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh2);
                    break;
                case 2://已完成
                    vh3 = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.all_order_items, null);
                    vh3.store = (TextView) view.findViewById(R.id.commit_item_store);
                    vh3.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
                    vh3.desc = (TextView) view.findViewById(R.id.commit_item_desc);
                    vh3.count = (TextView) view.findViewById(R.id.commit_item_count);
                    vh3.price = (TextView) view.findViewById(R.id.price);
                    vh3.order_codes = (TextView) view.findViewById(R.id.order_codes);
                    vh3.tv_address = (TextView) view.findViewById(R.id.tv_address);
                    vh3.Shipping_code=view.findViewById(R.id.Shipping_code);
                    vh3.Shipping_code.setVisibility(View.GONE);
                    vh3.tv_address.setVisibility(View.GONE);
                    vh3.type=view.findViewById(R.id.tv_type);
                    vh3.tv_evaluate=view.findViewById(R.id.tv_evaluate);
                    vh3.tv_evaluate.setVisibility(View.GONE);
                    view.setTag(vh3);
                    break;

            }


        } else {
            switch (type) {
                case 0:
                    vh1 = (ViewHolder) view.getTag();
                    break;
                case 1:
                    vh2 = (ViewHolder) view.getTag();
                    break;
                case 2:
                    vh3 = (ViewHolder) view.getTag();
                    break;

            }

        }

        String str="";
        switch (orderBean.getOrder_status_type()){//1、待发货，2、待支付，3、待收货，4、待评价，5、已取消，6、已完成
            case "1":
                str="待发货";//`
                break;
            case "3":
                str="待收货";
                break;
            case "4":
                str="待评价";
                break;
            case "5":
                str="已取消";
                break;
            case "6":
                str="已完成";//`
                break;
        }
        switch (type){
            case 0://全部
                if (str.equals("待发货")){
                    vh1.tv_evaluate.setText("设置发货");
                    vh1.tv_evaluate.setVisibility(View.VISIBLE);
                    if (!"".equals(orderBean.getAddress())){
                        vh1.tv_address.setVisibility(View.VISIBLE);
                        vh1.tv_address.setText("收货地址: "+orderBean.getAddress());

                    }
                    final ViewHolder finalVh = vh1;
                    vh1.tv_evaluate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show1(orderBean,finalVh.tv_evaluate, finalVh.type);
                        }
                    });
                }
                else {
                    vh1.tv_evaluate.setVisibility(View.GONE);
                    vh1.type.setText(str);
                }
                vh1.store.setText(orderBean.getStore_name());
                if (!"".equals(orderBean.getShipping_name())&&!"".equals(orderBean.getShipping_code())){
                    vh1.Shipping_code.setVisibility(View.VISIBLE);
                    vh1.Shipping_code.setText("物流公司: "+orderBean.getShipping_name()+"\n物流单号:"+orderBean.getShipping_code());
                }
                if (orderBean!=null&&orderBean.getGoods_list()!=null&&orderBean.getGoods_list().size()>0){
                    ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                    vh1.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                    vh1.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                    vh1.price.setText("共"+orderBean.getGoods_list().get(0).getGoods_num()+"件  合计￥"+orderBean.getTotal_amount());
                    vh1.order_codes.setText("订单编号: "+orderBean.getOrder_sn());
                }
                break;

            case 1://待发货
                if (str.equals("待发货")) {
                    vh2.type.setText(str);
                    vh2.tv_evaluate.setText("设置发货");
                    vh2.tv_evaluate.setVisibility(View.VISIBLE);
                    if (!"".equals(orderBean.getAddress())){
                        vh2.tv_address.setVisibility(View.VISIBLE);
                        vh2.tv_address.setText("收货地址: "+orderBean.getAddress());

                    }
                    final ViewHolder finalVh = vh1;
                    final ViewHolder finalVh2 = vh3;
                    vh2.tv_evaluate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            show1(orderBean,finalVh.tv_evaluate, finalVh2.type);
                        }
                    });
                }
                if (orderBean.getGoods_list()!=null&&orderBean.getGoods_list().size()>0){
                    vh2.store.setText(orderBean.getStore_name());
                    ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                    vh2.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                    vh2.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                    vh2.order_codes.setText("订单编号: "+orderBean.getOrder_sn());
                }

                break;
            case 2://已完成
                if (str.equals("已完成")){
                    vh3.type.setText(str);
                    vh3.tv_evaluate.setVisibility(View.GONE);
                    if (!"".equals(orderBean.getAddress())){
                        vh3.tv_address.setVisibility(View.VISIBLE);
                        vh3.tv_address.setText("收货地址: "+orderBean.getAddress());

                    }
                }
                if (!"".equals(orderBean.getShipping_code())&&!"".equals(orderBean.getShipping_name())){
                    vh3.Shipping_code.setVisibility(View.VISIBLE);
                    vh3.Shipping_code.setText("物流公司: "+orderBean.getShipping_name()+"\n物流单号: "+orderBean.getShipping_code());
                }
                if (orderBean.getGoods_list()!=null&&orderBean.getGoods_list().size()>0){

                    vh3.store.setText(orderBean.getStore_name());
                    ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
                    vh3.desc.setText(orderBean.getGoods_list().get(0).getGoods_name());
                    vh3.count.setText("X" +orderBean.getGoods_list().get(0).getGoods_num());
                    vh3.order_codes.setText("订单编号: "+orderBean.getOrder_sn());
                }
                break;
        }
        return view;
    }
    private EditText et_size,et_num;
    private Button btn_fig;
    private Dialog dialogs;
    private void show1(final OrderBean orderBean, final TextView tv, final TextView tv1) {
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
                setshipping(orderBean,shipping_code,shipping_name,tv,tv1);
            }
        });

    }

    private void setshipping(final OrderBean orderBean, String shipping_code, String shipping_name, final TextView tv, final TextView tv1) {
        if (TextUtils.isEmpty(shipping_code)){
            ToastUtils.showShort(context,"物流单号为空");
            return;
        }
        if (TextUtils.isEmpty(shipping_name)){
            ToastUtils.showShort(context,"物流商家名称为空");
            return;
        }
        Api.myorder_shipping(LoginManager.getInstance().getUserID(context),orderBean.getOrder_id(), shipping_code, shipping_name, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                Log.i("订单",apiResponse.toString());
                if (apiResponse.getCode()==Api.SUCCESS){
                    tv.setText("已发货");
                    tv1.setText("已发货");
                    list.remove(orderBean);
                    notifyDataSetChanged();
                }
                if (dialogs!=null&&dialogs.isShowing()){
                    dialogs.dismiss();
                }
            }

            @Override
            public void onFailure(String errMessage) {
                super.onFailure(errMessage);
                if (dialogs!=null&&dialogs.isShowing()){
                    dialogs.dismiss();
                }

            }
        });
    }

    private class ViewHolder {
        private TextView store, desc, count, price,type,tv_evaluate,order_codes,tv_address,Shipping_code;
        private ImageView iv;
    }
}
