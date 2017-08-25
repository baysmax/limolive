package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/25.
 */

public class StandardBean {
    private String good_id;
    private String good_size;
    private String s_id;

    private String store_count;

    @Override
    public String toString() {
        return "StandardBean{" +
                "good_id='" + good_id + '\'' +
                ", good_size='" + good_size + '\'' +
                ", s_id='" + s_id + '\'' +
                ", store_count='" + store_count + '\'' +
                '}';
    }

    public String getGood_id() {
        return good_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public String getGood_size() {
        return good_size;
    }

    public void setGood_size(String good_size) {
        this.good_size = good_size;
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
