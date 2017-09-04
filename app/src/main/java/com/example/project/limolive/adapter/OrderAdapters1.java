package com.example.project.limolive.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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

public class OrderAdapters1 extends BaseAdapter {
    private Context context;
    private List<OrderBean> list;
    private OrderBean orderBean;
    AlertDialog dialog = null;

    public OrderAdapters1(Context context, List<OrderBean> list) {
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
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder vh1=null;
        orderBean = list.get(i);
        if (view==null){
            vh1 = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.all_order_items, null);
            vh1.type=view.findViewById(R.id.tv_type);
            vh1.store = (TextView) view.findViewById(R.id.commit_item_store);
            vh1.iv = (ImageView) view.findViewById(R.id.commit_item_iv);
            vh1.desc = (TextView) view.findViewById(R.id.commit_item_desc);
            vh1.count = (TextView) view.findViewById(R.id.commit_item_count);
            vh1.Shipping_code = (TextView) view.findViewById(R.id.Shipping_code);
            vh1.tv_address = (TextView) view.findViewById(R.id.tv_address);
            vh1.tv_address.setVisibility(View.GONE);
            vh1.order_codes = (TextView) view.findViewById(R.id.order_codes);
            vh1.Shipping_code.setVisibility(View.GONE);
            vh1.price = (TextView) view.findViewById(R.id.price);
            vh1.tv_evaluate=view.findViewById(R.id.tv_evaluate);
            vh1.tv_evaluate.setVisibility(View.GONE);
            vh1.tv_evaluate1=view.findViewById(R.id.tv_evaluate1);
            vh1.tv_evaluate1.setVisibility(View.GONE);
            view.setTag(vh1);
        }else {
            vh1 = (ViewHolder) view.getTag();
        }
        String str="";
        switch (this.orderBean.getStatus_id()){//0、待发货，2、待支付，3、待收货，4、待评价，5、已取消，6、已完成
            case "0":
                str="审核";
                break;
            case "1":
                str="审核通过";
                break;
            case "2":
                str="已完成";
                break;

        }
        if ("审核".equals(str)){
            vh1.tv_evaluate.setText("审核");
            vh1.tv_evaluate.setVisibility(View.VISIBLE);
            final ViewHolder finalVh = vh1;
            vh1.type.setText("退货审核");
            vh1.tv_evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Api.myorder_sell_up(orderBean.getId(),LoginManager.getInstance().getUserID(context), "1", new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        Log.i("售后","apiResponse="+apiResponse.toString());
                        if (apiResponse.getCode()==Api.SUCCESS){
                            finalVh.tv_evaluate.setText("已通过");
                            finalVh.type.setText("退货申请已通过");
                        }

                    }
                });

                }
            });
        }else if ("审核通过".equals(str)){
            vh1.tv_evaluate.setText("已通过");
            vh1.tv_evaluate.setVisibility(View.VISIBLE);
            vh1.type.setText(str);
            if (!"".equals(orderBean.getShipping_code())&&!"".equals(orderBean.getShipping_name())){
                vh1.Shipping_code.setVisibility(View.VISIBLE);
                vh1.Shipping_code.setText("物流公司: "+orderBean.getShipping_name()+" 物流单号:\n"+orderBean.getShipping_code());
                vh1.tv_evaluate.setText("确认收货");
                final ViewHolder finalVh1 = vh1;
                vh1.tv_evaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Api.myorder_sell_up(orderBean.getId(), LoginManager.getInstance().getUserID(context), "2", new ApiResponseHandler(context) {
                            @Override
                            public void onSuccess(ApiResponse apiResponse) {
                                if (apiResponse.getCode()==Api.SUCCESS){
                                    finalVh1.tv_evaluate.setText("已完成");
                                    finalVh1.type.setText("已完成");
                                    finalVh1.tv_evaluate.setEnabled(false);
                                    finalVh1.tv_evaluate1.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                });
                vh1.type.setText("对方已发货");
                vh1.tv_evaluate1.setVisibility(View.VISIBLE);
                vh1.tv_evaluate1.setText("查看物流");

                vh1.tv_evaluate1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", orderBean.getShipping_code());
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        ToastUtils.showShort(context,"订单信息已复制到粘贴板");
                    }
                });
            }
        }else if ("已完成".equals(str)){
            vh1.tv_evaluate.setText(str);
            vh1.tv_evaluate.setVisibility(View.VISIBLE);
            vh1.Shipping_code.setVisibility(View.VISIBLE);
            if (!"".equals(orderBean.getShipping_code())&&!"".equals(orderBean.getShipping_name())){
                vh1.Shipping_code.setVisibility(View.VISIBLE);
                vh1.Shipping_code.setText("物流公司:　"+orderBean.getShipping_name()+"\n物流单号:　"+orderBean.getShipping_code());
            }
            if (!"".equals(orderBean.getAddress())){
                vh1.tv_address.setVisibility(View.VISIBLE);
                vh1.tv_address.setText("收货地址: "+orderBean.getAddress());
            }
            vh1.type.setText(str);
        }
        if (orderBean!=null&&orderBean.getGoods_list()!=null&&orderBean.getGoods_list().size()>0){
            vh1.store.setText(this.orderBean.getStore_name());
            ImageLoader.getInstance().displayImage(ApiHttpClient.API_PIC + this.orderBean.getGoods_list().get(0).getOriginal_img(), vh1.iv);
            vh1.desc.setText(this.orderBean.getGoods_list().get(0).getGoods_name());
            vh1.count.setText("X" + this.orderBean.getGoods_list().get(0).getGoods_num());
            vh1.price.setText("￥"+this.orderBean.getGoods_list().get(0).getGoods_price());
            vh1.order_codes.setText("订单编号: "+orderBean.getOrder_id());
        }
        return view;
    }

//    private EditText et_size,et_num;
//    private Button btn_fig;
//    private Dialog dialogs;
//    private void show1(final ViewHolder finalVh) {
//        View view = View.inflate(context, R.layout.dialogs1, null);
//        et_size = view.findViewById(R.id.et_size);
//        et_num = view.findViewById(R.id.et_num);
//        btn_fig = view.findViewById(R.id.btn_fig);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                .setTitle("请输入物流信息")
//                .setView(view);
//        dialogs = builder.show();
//        btn_fig.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String shipping_code=et_num.getText().toString();
//                String  shipping_name=et_size.getText().toString();
//                setShipping(shipping_code,shipping_name,finalVh);
//            }
//
//
//        });
//
//    }

//    private void setShipping(String shipping_code, String shipping_name, final ViewHolder finalVh) {
//        if (TextUtils.isEmpty(shipping_code)){
//            ToastUtils.showShort(context,"物流单号为空");
//            return;
//        }
//        if (TextUtils.isEmpty(shipping_name)){
//            ToastUtils.showShort(context,"物流商家名称为空");
//            return;
//        }
//        Api.order_return_add(orderBean.getOrder_id(),orderBean.getId(), shipping_code, shipping_name, new ApiResponseHandler(context) {
//            @Override
//            public void onSuccess(ApiResponse apiResponse) {
//                if (apiResponse.getCode()==Api.SUCCESS){
//                    finalVh.type.setText("退货成功");
//                    finalVh.tv_evaluate.setVisibility(View.GONE);
//                    if (dialogs!=null){
//                        dialogs.dismiss();
//                    }
//                }
//            }
//        });
//    }

    private class ViewHolder {
        private TextView store, desc, count, price,type,tv_evaluate,tv_evaluate1,Shipping_code,order_codes,tv_address;
        private ImageView iv;
    }
}
