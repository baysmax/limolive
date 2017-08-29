package com.example.project.limolive.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.adapter.OrderAdapters;
import com.example.project.limolive.adapter.OrderAdapters1;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.order.OrderBean;
import com.example.project.limolive.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.project.limolive.presenter.ShopCartPresenter.CART_LIST_OVER;

/**
 * Created by ZL on 2017/2/23.
 */

public class OrderPresenters1 extends Presenter {

    private List<OrderBean> list;
    private ListView listView;
    private OrderAdapters1 adapter;
    public OrderPresenters1(Context context) {
        super(context);
    }


    /**
     * 获取商家售后列表
     *
     * @param user_id 用户id
     */
    public void getOrder(String user_id, final String page){
        Api.myOrderSellAfter(user_id,page, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {

                Log.i("热门商品类型","apiResponse.getData()"+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    if ("1".equals(page)){
                        list.clear();
                    }
                    list.addAll(JSONArray.parseArray(apiResponse.getData(),OrderBean.class));
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(context,apiResponse.getMessage());
                }
                if(tellActivity!=null){
                    if(list.size()==0){
                        tellActivity.presenterTakeAction(1);
                    }else {
                        tellActivity.presenterTakeAction(2);
                    }
                    tellActivity.presenterTakeAction(CART_LIST_OVER);
                }
            }
        });
    }
    public void getOrder(String user_id, final String page,String type){
        Api.myOrderReturnList(user_id,page, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {

                Log.i("热门商品类型","apiResponse.getData()"+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    if ("1".equals(page)){
                        list.clear();
                    }
                    list.addAll(JSONArray.parseArray(apiResponse.getData(),OrderBean.class));
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(context,apiResponse.getMessage());
                }
                if(tellActivity!=null){
                    if(list.size()==0){
                        tellActivity.presenterTakeAction(1);
                    }else {
                        tellActivity.presenterTakeAction(2);
                    }
                    tellActivity.presenterTakeAction(CART_LIST_OVER);
                }
            }
        });
    }
    /**
     * 设置适配器
     */
    public void setAdapter(ListView listView){
        this.listView = listView;
        list = new ArrayList<>();
        if(adapter ==null){
            adapter = new OrderAdapters1(context,list);
            listView.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refresh() {
        super.refresh();
    }
}
