package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/25.
 */

public class StandardBean {
    private String goods_id;
    private String goods_size;
    private String s_id;
    private String store_count;

    @Override
    public String toString() {
        return "StandardBean{" +
                "goods_id='" + goods_id + '\'' +
                ", goods_size='" + goods_size + '\'' +
                ", s_id='" + s_id + '\'' +
                ", store_count='" + store_count + '\'' +
                '}';
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_size() {
        return goods_size;
    }

    public void setGoods_size(String goods_size) {
        this.goods_size = goods_size;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getStore_count() {
        return store_count;
    }

    public void setStore_count(String store_count) {
        this.store_count = store_count;
    }
}
