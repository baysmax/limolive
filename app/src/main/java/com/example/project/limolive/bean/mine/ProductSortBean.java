package com.example.project.limolive.bean.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：黄亚菲 on 2017/2/21 14:46
 * 功能：商品分类模型
 */
public class ProductSortBean implements Parcelable{

//    "id": "1",
//            "name": "服装",
//            "type_img": "/data/upload/2016/11-28/583bf1bfeb1d1.png"

    private String id;
    private String name;
    private String type_img;


    public ProductSortBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        type_img = in.readString();
    }

    public static final Creator<ProductSortBean> CREATOR = new Creator<ProductSortBean>() {
        @Override
        public ProductSortBean createFromParcel(Parcel in) {
            return new ProductSortBean(in);
        }

        @Override
        public ProductSortBean[] newArray(int size) {
            return new ProductSortBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_img() {
        return type_img;
    }

    public void setType_img(String type_img) {
        this.type_img = type_img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(type_img);
    }
}
