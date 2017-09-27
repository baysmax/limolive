package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/9/27.
 */

public class CoinListBean {
    /**
     * lemon_id（编号id）、lemon_coins（钻石币）、price（真实金额）、nums（钻石币数量）
     */
    private String lemon_id;
    private String lemon_coins;
    private String price;
    private String nums;

    public String getLemon_id() {
        return lemon_id;
    }

    public void setLemon_id(String lemon_id) {
        this.lemon_id = lemon_id;
    }

    public String getLemon_coins() {
        return lemon_coins;
    }

    public void setLemon_coins(String lemon_coins) {
        this.lemon_coins = lemon_coins;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }
}
