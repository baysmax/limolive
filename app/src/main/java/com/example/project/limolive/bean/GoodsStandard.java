package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/26.
 */

public class GoodsStandard {
    private String good_size;
    private String store_count;

    public GoodsStandard() {
    }

    public GoodsStandard(String good_size, String store_count) {
        this.good_size = good_size;
        this.store_count = store_count;
    }

    public String getGood_size() {
        return good_size;
    }

    public void setGood_size(String good_size) {
        this.good_size = good_size;
    }

    public String getStore_count() {
        return store_count;
    }

    public void setStore_count(String store_count) {
        this.store_count = store_count;
    }

    @Override
    public String toString() {
        return "GoodsStandard{" +
                "good_size='" + good_size + '\'' +
                ", store_count='" + store_count + '\'' +
                '}';
    }
}
