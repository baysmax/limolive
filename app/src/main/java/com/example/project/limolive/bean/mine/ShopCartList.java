package com.example.project.limolive.bean.mine;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.project.limolive.bean.ShopCartBean;

/**
 * 结算后的购物车列表
 * @author hwj on 2016/12/26.
 */

public class ShopCartList implements Parcelable{

    private String stores_name;
    private ShopCartBean data;

    public ShopCartList(){}

    protected ShopCartList(Parcel in) {
        stores_name = in.readString();
        data = in.readParcelable(ShopCartBean.class.getClassLoader());
    }

    public static final Creator<ShopCartList> CREATOR = new Creator<ShopCartList>() {
        @Override
        public ShopCartList createFromParcel(Parcel in) {
            return new ShopCartList(in);
        }

        @Override
        public ShopCartList[] newArray(int size) {
            return new ShopCartList[size];
        }
    };

    public String getStores_name() {
        return stores_name;
    }

    public void setStores_name(String stores_name) {
        this.stores_name = stores_name;
    }

    public ShopCartBean getData() {
        return data;
    }

    public void setData(ShopCartBean data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stores_name);
        dest.writeParcelable(data, flags);
    }
}
