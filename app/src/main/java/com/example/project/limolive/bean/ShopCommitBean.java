package com.example.project.limolive.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.project.limolive.bean.mine.ShopCartList;

/**
 * 提交结算
 * @author hwj on 2016/12/26.
 */

public class ShopCommitBean implements Parcelable{

    private ShopAddress addressList; //默认收货地址
    private ShopCartList cartList;  //购物车列表
    private String totalPrice; //总价

    public ShopCommitBean(){}

    protected ShopCommitBean(Parcel in) {
        addressList = in.readParcelable(ShopAddress.class.getClassLoader());
        cartList = in.readParcelable(ShopCartList.class.getClassLoader());
        totalPrice = in.readString();
    }

    public static final Creator<ShopCommitBean> CREATOR = new Creator<ShopCommitBean>() {
        @Override
        public ShopCommitBean createFromParcel(Parcel in) {
            return new ShopCommitBean(in);
        }

        @Override
        public ShopCommitBean[] newArray(int size) {
            return new ShopCommitBean[size];
        }
    };

    public ShopAddress getAddressList() {
        return addressList;
    }

    public void setAddressList(ShopAddress addressList) {
        this.addressList = addressList;
    }

    public ShopCartList getCartList() {
        return cartList;
    }

    public void setCartList(ShopCartList cartList) {
        this.cartList = cartList;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(addressList, flags);
        dest.writeParcelable(cartList, flags);
        dest.writeString(totalPrice);
    }
}
