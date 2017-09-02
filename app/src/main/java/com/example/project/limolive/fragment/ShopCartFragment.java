package com.example.project.limolive.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.project.limolive.R;
import com.example.project.limolive.activity.BaseActivity;
import com.example.project.limolive.presenter.Presenter;
import com.example.project.limolive.presenter.ShopCartPresenter;
import com.example.project.limolive.widget.AutoSwipeRefreshLayout;

/**
 * 购物车
 * <p>主要的核心逻辑由{@link ShopCartPresenter}处理，本页面需要注意右上角的编辑，点击编辑后，下方按钮变为删除两字
 * 即调用删除接口，删除完毕后需要刷新页面</p>
 * @author hwj on 2016/12/21.
 */

public class ShopCartFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        Presenter.NotificationToActivity,View.OnClickListener{

    private ListView lv_cart_list;
    private AutoSwipeRefreshLayout swipe_cart;

    private ShopCartPresenter shopCartPresenter;

    private ImageView iv_all_select;  //全选
    private TextView tv_goods_number; //总数
    private TextView tv_all_money; //总价

    private Button btn_calculate_price;//结算或者删除
    private LinearLayout ll_good_info;//中间商品信息显示区域




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return setContentView(R.layout.fragment_shop_cart,inflater, container);
    }

    @Override
    protected void initView() {
        super.initView();
        shopCartPresenter=new ShopCartPresenter(getActivity(),swipe_cart);
        shopCartPresenter.registerMsgToActivity(this);
        //loadTitle();

        lv_cart_list= (ListView) findViewById(R.id.lv_cart_list);

        iv_all_select= (ImageView) findViewById(R.id.iv_all_select);
        iv_all_select.setOnClickListener(this);
        tv_goods_number= (TextView) findViewById(R.id.tv_goods_number);
        tv_all_money= (TextView) findViewById(R.id.tv_all_money);

        btn_calculate_price= (Button) findViewById(R.id.btn_calculate_price);
        btn_calculate_price.setOnClickListener(this);

        ll_good_info= (LinearLayout) findViewById(R.id.ll_good_info);

        swipe_cart= (AutoSwipeRefreshLayout) findViewById(R.id.swipe_cart);
        swipe_cart.setColorSchemeColors(BaseActivity.STATUS_BAR_COLOR_DEFAULT);
        swipe_cart.setOnRefreshListener(this);
        shopCartPresenter.getAdapter(lv_cart_list);
    }

    private void loadTitle() {
        setTitleString(getString(R.string.mine_shop_cart));
        setLeftImage(R.mipmap.icon_return);
        setRightText(getString(R.string.mine_edit));
        setLeftRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        setRightRegionListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopCartPresenter.isEditGood()){ //编辑状态为完成
                    shopCartPresenter.isEditGood(!shopCartPresenter.isEditGood());
                    updateUI(shopCartPresenter.isEditGood());
                }else{
                    shopCartPresenter.editCart();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!swipe_cart.isRefreshing()){
            swipe_cart.autoRefresh();
        }
    }

    @Override
    public void onRefresh() {
        shopCartPresenter.refresh();
    }

    @Override
    public void presenterTakeAction(int action) {
        if(ShopCartPresenter.CART_LIST_OVER==action){
            swipe_cart.setRefreshing(false);
        }else if(ShopCartPresenter.CALCULATE_PRICE==action){//计算价格
            tv_all_money.setText("¥"+shopCartPresenter.getPrice()+"");
            tv_goods_number.setText("共"+shopCartPresenter.getAll_number()+"件");
        }else if(ShopCartPresenter.EDIT_CART==action){ //编辑 需要更换下方UI
            updateUI(shopCartPresenter.isEditGood());
        }else if(ShopCartPresenter.LIST_REFRESH==action){
            if(!swipe_cart.isRefreshing()){
                swipe_cart.autoRefresh();
            }
        }
    }

    private void updateUI(boolean isEdit) {
        if(isEdit){
            btn_calculate_price.setText(getString(R.string.mine_cart_delete));
            setRightText(getString(R.string.mine_cart_over));
            ll_good_info.setVisibility(View.GONE);
        }else{
            btn_calculate_price.setText(getString(R.string.mine_calculate_price));
            setRightText(getString(R.string.mine_edit));
            ll_good_info.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_all_select:
                boolean s=iv_all_select.isSelected();
                iv_all_select.setSelected(!s);
                shopCartPresenter.allSelect(iv_all_select.isSelected());
                break;
            case R.id.btn_calculate_price:
                calculate();
                break;
        }
    }

    private void calculate() {
        if(shopCartPresenter.isEditGood()){ //编辑状态为删除购物车商品，否则结算
            shopCartPresenter.deleteGood();
        }else{
            shopCartPresenter.calculateOrder();
        }
    }
}
