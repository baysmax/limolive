package com.example.project.limolive.bean.mine;

/**
 * 作者：黄亚菲 on 2017/2/23 16:25
 * 功能：浏览足迹
 */
public class FootMarkBean {


//    "footmark_id": "20",
//            "add_time": "1483002613",
//            "goods_id": "5",
//            "goods_name": "ggggggg",
//            "shop_price": "599.00",
//            "original_img": "/data/upload/2016/12-01/583fcd21b913f.jpg"


    private String footmark_id;
    private String add_time;
    private String goods_id;
    private String goods_name;
    private String shop_price;
    private String original_img;


    public FootMarkBean(String footmark_id, String add_time, String goods_id, String goods_name, String shop_price, String original_img) {
        this.footmark_id = footmark_id;
        this.add_time = add_time;
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.shop_price = shop_price;
        this.original_img = original_img;
    }

    public FootMarkBean() {
    }

    public String getFootmark_id() {
        return footmark_id;
    }

    public void setFootmark_id(String footmark_id) {
        this.footmark_id = footmark_id;
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

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }


    @Override
    public String toString() {
        return "FootMarkBean{" +
                "footmark_id='" + footmark_id + '\'' +
                ", add_time='" + add_time + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", shop_price='" + shop_price + '\'' +
                ", original_img='" + original_img + '\'' +
                '}';
    }
}
