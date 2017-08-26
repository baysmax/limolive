package com.example.project.limolive.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.example.project.limolive.adapter.OrderAdapter;
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

public class OrderPresenter extends Presenter {

    private List<OrderBean> list;
    private ListView listView;
    private OrderAdapter adapter;
    private String type;
    public OrderPresenter(Context context) {
        super(context);
    }


    /**
     * 获取用户订单
     *
     * @param user_id 用户id
     * @param type    订单的类型,不传默认查询所有
     */
    public void getOrder(String user_id,String type){
        this.type=type;
        Api.getOrder(user_id,type, new ApiResponseHandler(context) {

            @Override
            public void onSuccess(ApiResponse apiResponse) {

                Log.i("热门商品类型","apiResponse.getData()"+apiResponse.toString());
                if (apiResponse.getCode() == Api.SUCCESS) {
                    list.clear();
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
            adapter = new OrderAdapter(context,list,type);
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
