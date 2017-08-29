package com.example.project.limolive.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.adapter.OrderAdapters1;
import com.example.project.limolive.adapter.OrderAdapters2;
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

public class OrderPresenters2 extends Presenter {

    private List<OrderBean> list;
    private ListView listView;
    private OrderAdapters2 adapter;
    public OrderPresenters2(Context context) {
        super(context);
    }

    /**
     * 买家列表
     * @param user_id
     * @param page
     * @param type
     */
    public void getOrder(String user_id, final String page,String type){
        if ("1".equals(page)){
            list.clear();
        }
        Api.myOrderReturnList(user_id,page, new ApiResponseHandler(context) {
            @Override
            public void onSuccess(ApiResponse apiResponse) {

                Log.i("热门商品类型","apiResponse.getData()"+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    Log.i("售后","page"+page);
                    if ("1".equals(page)){
                        Log.i("售后","page="+page);
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
            adapter = new OrderAdapters2(context,list);
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
