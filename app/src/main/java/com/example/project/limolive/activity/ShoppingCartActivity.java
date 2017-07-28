package com.example.project.limolive.activity;

import android.os.Bundle;

import com.example.project.limolive.R;
import com.example.project.limolive.fragment.ShopCartFragment;

/**
 * 购物车
 * @author hwj on 2016/12/19.
 */

public class ShoppingCartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);

        ShopCartFragment mFindGoodsFragment=new ShopCartFragment();
        loadFragment(mFindGoodsFragment,R.id.fragment_container);
    }
}
