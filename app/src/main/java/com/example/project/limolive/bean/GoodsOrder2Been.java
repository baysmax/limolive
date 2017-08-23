package com.example.project.limolive.bean;/**
 * Created by Administrator on 2017/3/23.
 */

/**
 * GoodsOrder2Been
 * 作者：李志超 on 2017/3/23 13:57
 */

public class GoodsOrder2Been {
    private String postFee;

    public String getOrder_sns() {
        return order_sns;
    }

    public void setOrder_sns(String order_sns) {
        this.order_sns = order_sns;
    }

    private String order_sns;

    @Override
    public String toString() {
        return "GoodsOrder2Been{" +
                "postFee='" + postFee + '\'' +
                ", order_sns='" + order_sns + '\'' +
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
