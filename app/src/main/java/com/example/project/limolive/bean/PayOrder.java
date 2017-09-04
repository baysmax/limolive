package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/9/4.
 */

public class PayOrder {
    private String  order_sn;

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    @Override
    public String toString() {
        return "PayOrder{" +
                "order_sn='" + order_sn + '\'' +
                ", order_amount='" + order_amount + '\'' +
                '}';
    }

    private String  order_amount;
}
