package com.example.project.limolive.bean;/**
 * Created by Administrator on 2017/3/23.
 */

/**
 * GoodsOrder2Been
 * 作者：李志超 on 2017/3/23 13:57
 */

public class GoodsOrder2Been {
    private String postFee;

    @Override
    public String toString() {
        return "GoodsOrder2Been{" +
                "postFee='" + postFee + '\'' +
                ", payables='" + payables + '\'' +
                ", goodsFee='" + goodsFee + '\'' +
                ", order_sn='" + order_sn + '\'' +
                '}';
    }

    private String payables;
    private String goodsFee;
    private String order_sn;

    public String getPostFee() {
        return postFee;
    }

    public void setPostFee(String postFee) {
        this.postFee = postFee;
    }

    public String getPayables() {
        return payables;
    }

    public void setPayables(String payables) {
        this.payables = payables;
    }

    public String getGoodsFee() {
        return goodsFee;
    }

    public void setGoodsFee(String goodsFee) {
        this.goodsFee = goodsFee;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
}
