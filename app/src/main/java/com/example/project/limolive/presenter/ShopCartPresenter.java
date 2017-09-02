package com.example.project.limolive.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.project.limolive.R;
import com.example.project.limolive.activity.CommitOrdersActivity;
import com.example.project.limolive.adapter.ShopCartAdapter;
import com.example.project.limolive.api.Api;
import com.example.project.limolive.api.ApiResponse;
import com.example.project.limolive.api.ApiResponseHandler;
import com.example.project.limolive.bean.ShopCartBean;
import com.example.project.limolive.fragment.ShopCartFragment;
import com.example.project.limolive.helper.DialogFactory;
import com.example.project.limolive.helper.LoginManager;
import com.example.project.limolive.utils.NetWorkUtil;
import com.example.project.limolive.utils.ToastUtils;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * <p>{@link ShopCartFragment} 界面的处理逻辑，点击右上角编辑则编辑购物车，调用editCart(),发送编辑信息到UI层</p>
 * <p>本类处理购物车列表的增删以及变换数量的相关逻辑，在每做一次相关操作后，都需要实时更新相关页面的数据，即调用
 * adapter.notifyDataSetChanged()方法，并在调用此方法前必须计算已选择的商品的总价钱，参看updateGoodStatus()方法
 * </p>
 * <p>请参照{@link ShopCartBean}类， 有两个特殊的字段 isSelect，start_number，第一个为是否选中，第二个为从服务器上
 * 更新购物车列表的商品数量，在点击下方全选按钮时，需要遍历当前购物车列表，isSelect全部置为1，即代表全选；对于start_number，
 * 则是判断是否应当修改本商品的数量，在点击结算时，需要进行2步，第一步是修改当前购物车，需要向服务器提交数据，未做修改
 * 则调用提交接口，参见本类的calculateOrder()方法
 * </p>
 * <p>具体注册点击事件参照{@link ShopCartAdapter}</p>
 *
 * @author hwj on 2016/12/21.
 */

public class ShopCartPresenter extends Presenter implements ShopCartAdapter.CartButtonListener {

    public static final int CART_LIST_OVER = 0; //下载列表完毕
    public static final int EDIT_CART = 1;//编辑购物车
    public static final int CALCULATE_PRICE = 2;//计算总价
    public static final int LIST_REFRESH = 3;//刷新列表

    private ShopCartAdapter adapter;
    private List<ShopCartBean> data;
    private float price;//商品总价
    private int all_number;//商品总数（已选择的）

    private ListView listView;
    private boolean isEditGood = false;//是否开启编辑模式
    private AutoSwipeRefreshLayout swipe_cart;


    public ShopCartPresenter(Context context,AutoSwipeRefreshLayout swipe_cart) {
        super(context);
        this.swipe_cart=swipe_cart;
    }

    public ListAdapter getAdapter() {
        if (adapter == null) {
            data = new ArrayList<>();
            adapter = new ShopCartAdapter(context, data);
            adapter.setCartButtonListener(this);
        }
        return adapter;
    }

    public void getAdapter(ListView listView) {
        if (listView == null) {
            return;
        }
        this.listView = listView;
        if (adapter == null) {
            data = new ArrayList<>();
            adapter = new ShopCartAdapter(context, data);
            adapter.setCartButtonListener(this);
            listView.setAdapter(adapter);
        }
    }

    /**
     * 编辑购物车
     */
    public void editCart() {
        isEditGood = true;
        if (tellActivity != null) {
            tellActivity.presenterTakeAction(EDIT_CART);
        }
    }

    public boolean isEditGood() {
        return isEditGood;
    }

    //控制本变量
    public void isEditGood(boolean isEditGood) {
        this.isEditGood = isEditGood;
    }

    @Override
    protected void getDate(boolean isClear) {
        super.getDate(isClear);
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        Api.cartList(LoginManager.getInstance().getUserID(context),
                new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode() == Api.SUCCESS) {
                            handleData(apiResponse.getData());
                            if (swipe_cart!=null){
                                swipe_cart.setRefreshing(false);
                            }
                        } else {
                            ToastUtils.showShort(context, apiResponse.getMessage());
                        }
                        if (tellActivity != null) {
                            tellActivity.presenterTakeAction(CART_LIST_OVER);
                        }
                    }

                    @Override
                    public void onFailure(String errMessage) {
                        super.onFailure(errMessage);
                        if (swipe_cart!=null){
                            swipe_cart.setRefreshing(false);
                        }
                    }
                });
    }

    /**
     * 处理相关数据
     *
     * @param json
     */
    private void handleData(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
        List<ShopCartBean> list = JSON.parseArray(json, ShopCartBean.class);
        if (list != null && list.size() > 0) {
            for (ShopCartBean bean : list) {
                bean.compareNumber(); //保存一个原始数据，方便比较
            }
        }
        data.clear();
        data.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void refresh() {
        super.refresh();
        getDate(true);
    }

    /**
     * 添加商品数量
     *
     * @param v
     * @param position
     */
    @Override
    public void addGoodNumber(View v, int position) {
        ShopCartBean bean = data.get(position);
        int num = Integer.parseInt(bean.getGoods_num());
        num++;
        bean.setGoods_num(String.valueOf(num));
        updateGoodStatus();
        //adapter.updateItem(listView,position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 计算数量和价格
     */
    private void updateGoodStatus() {
        calculateNumber();
        calculatePrice();
    }

    /**
     * 减少商品数量
     *
     * @param v
     * @param position
     */
    @Override
    public void reduceNumber(View v, int position) {
        ShopCartBean bean = data.get(position);
        int num = Integer.parseInt(bean.getGoods_num());
        if (num == 1) {
            ToastUtils.showShort(context, getString(R.string.mine_cart_number));
            return;
        }
        num--;
        bean.setGoods_num(String.valueOf(num));
        updateGoodStatus();
        //adapter.updateItem(listView,position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 选择 or 是否选择
     *
     * @param v
     * @param position
     */
    @Override
    public void isSelected(View v, int position) {
        ShopCartBean bean = data.get(position);
        int isSelect = bean.getIsSelect();
        if (isSelect == 0) {
            isSelect = 1;
        } else {
            isSelect = 0;
        }
        bean.setIsSelect(isSelect);
        updateGoodStatus();
//       adapter.updateItem(listView,position);
        adapter.notifyDataSetChanged();
    }

    /**
     * 计算总价
     */
    public void calculatePrice() {
        price = 0;
        if (data == null || data.size() == 0) {
            return;
        }
        for (ShopCartBean bean : data) {
            if (bean.getIsSelect() == 0) {
                continue;
            }
            float s = Integer.parseInt(bean.getGoods_num())
                    * Float.parseFloat(bean.getGoods_price());
            price += s;
        }
        if (tellActivity != null) {
            tellActivity.presenterTakeAction(CALCULATE_PRICE);
        }
    }

    /**
     * 全选
     */
    public void allSelect(boolean isSelect) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (isSelect) {
            for (ShopCartBean bean : data) {
                if (bean.getIsSelect() == 0) {
                    bean.setIsSelect(1);
                }
            }
        } else {
            for (ShopCartBean bean : data) {
                if (bean.getIsSelect() == 1) {
                    bean.setIsSelect(0);
                }
            }
        }
        updateGoodStatus();
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取商品总价
     *
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     * 获取已选择的商品总数
     *
     * @return
     */
    public int getAll_number() {
        return all_number;
    }

    /**
     * 计算总数
     *
     * @return
     */
    public void calculateNumber() {
        all_number = 0;
        if (data == null || data.size() == 0) {
            return;
        }
        for (ShopCartBean bean : data) {
            if (bean.getIsSelect() == 0) {
                continue;
            }
            int num = Integer.parseInt(bean.getGoods_num());
            all_number += num;
        }
    }

    /**
     * 删除选中的商品
     */
    public void deleteGood() {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getIsSelect() == 1) {
                builder.append(data.get(i).getId())
                        .append(",");
            }
        }
        String ids = builder.toString();
        if (builder.length() > 0 && builder.charAt(builder.length() - 1) == ',') {
            ids = builder.substring(0, builder.length() - 1);
        }
        if (TextUtils.isEmpty(ids)) {
            return;
        }
        final ProgressDialog dialog = DialogFactory.getDefualtProgressDialog((Activity) context, DELETING);
        dialog.show();
        Api.deleteCart(LoginManager.getInstance().getUserID(context), ids
                , new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode() == Api.SUCCESS) {
                            if (tellActivity != null) {
                                tellActivity.presenterTakeAction(LIST_REFRESH);
                            }
                        } else {
                            ToastUtils.showShort(context, apiResponse.getMessage());
                        }
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

    }

    /**
     * 结算 多个商品首先需要修改购物车信息，然后结算
     */
    public void calculateOrder() {
        if (!NetWorkUtil.isNetworkConnected(context)) {
            ToastUtils.showShort(context, NET_UNCONNECT);
            return;
        }
        if (data == null || data.size() == 0) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        StringBuilder id = new StringBuilder();
        for (ShopCartBean bean : data) {
            if (bean.getIsSelect() == 1) {
                if (bean.isChange()) {
                    builder.append(bean.getId())
                            .append(":")
                            .append(bean.getGoods_num())
                            .append(";");
                }
                id.append(bean.getId())
                        .append(",");
            }
        }
        String ids = builder.toString();
        String id_e = id.toString();
        if (TextUtils.isEmpty(id_e)) {
            ToastUtils.showShort(context, getString(R.string.mine_cart_select_good));
            return;
        }
        if (builder.length() > 0 && builder.charAt(builder.length() - 1) == ';') {
            ids = builder.substring(0, builder.length() - 1);
        }
        if (id.length() > 0 && id.charAt(id.length() - 1) == ',') {
            id_e = id.substring(0, id.length() - 1);
        }
        final String id_c = id_e;
        final ProgressDialog dialog = DialogFactory.getDefualtProgressDialog((Activity) context, CHANGING);
        dialog.show();
        if (TextUtils.isEmpty(ids)) {
            commit(id_c, dialog);
//            Intent intent = new Intent(context, CommitOrdersActivity.class);
//            intent.putExtra("ids",id_c);
//            context.startActivity(intent);
            return;
        }
        Api.changeCart(LoginManager.getInstance().getUserID(context), ids
                , new ApiResponseHandler(context) {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (apiResponse.getCode() == Api.SUCCESS) {
                            commit(id_c, dialog);
                        } else {
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }
                            ToastUtils.showShort(context, apiResponse.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(String errMessage) {
                        super.onFailure(errMessage);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });

    }

    /**
     * 最终提交购物车
     */
    private void commit(final String ids, final ProgressDialog dialog) {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Intent intent = new Intent(context, CommitOrdersActivity.class);
        intent.putExtra("ids",ids);
        context.startActivity(intent);
//        Api.commitCart(LoginManager.getInstance().getUserID(context), ids, new ApiResponseHandler(context) {
//            @Override
//            public void onSuccess(ApiResponse apiResponse) {
//                if (apiResponse.getCode() == Api.SUCCESS) {
//
//                    //handleCommit(apiResponse.getData());
//                }
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//            }
//
//            @Override
//            public void onFailure(String errMessage) {
//                super.onFailure(errMessage);
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//            }
//        });
    }

    /**
     * 提交
     *
     * @param json
     */
    private void handleCommit(String json) {
        if (TextUtils.isEmpty(json)) {
            return;
        }
//        Intent intent = new Intent(context, CommitOrdersActivity.class);
//        Log.e("A",json);
////        ShopCommitBean bean=JSON.parseObject(json,ShopCommitBean.class);
//        //TODO 跳到相关页面 需要传送相应数据到下个页面
//        context.startActivity(intent);
    }
}
