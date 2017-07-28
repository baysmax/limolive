package com.example.project.limolive.bean.mine;

/**
 * 作者：黄亚菲 on 2017/2/23 14:45
 * 功能：我的收藏列表
 */
public class MyCollectBean {


//    "collect_id": "20",
//            "add_time": "1483002613",
//            "goods_id": "5",
//            "goods_name": "ggggggg",
//            "shop_price": "599.00",
//            "store_count":"12",
//            "original_img": "/data/upload/2016/12-01/583fcd21b913f.jpg"

    private String collect_id;
    private String add_time;
    private String goods_id;
    private String goods_name;
    private String shop_price;
    private String store_count;
    private String original_img;


    public MyCollectBean(String collect_id, String add_time, String goods_id, String goods_name, String shop_price, String store_count, String original_img) {
        this.collect_id = collect_id;
        this.add_time = add_time;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.shop_price = shop_price;
        this.store_count = store_count;
        this.original_img = original_img;
    }

    public MyCollectBean() {
    }

    public String getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(String collect_id) {
        this.collect_id = collect_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getShop_price() {
        return shop_price;
    }

    public void setShop_price(String shop_price) {
        this.shop_price = shop_price;
    }

    public String getStore_count() {
        return store_count;
    }

    public void setStore_count(String store_count) {
        this.store_count = store_count;
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }

    @Override
    public String toString() {
        return "MyCollectBean{" +
                "collect_id='" + collect_id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", store_count='" + store_count + '\'' +
                ", original_img='" + original_img + '\'' +
                '}';
    }
}
