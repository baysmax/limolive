package com.example.project.limolive.bean;/**
 * Created by Administrator on 2017/3/23.
 */

/**
 * RechargeBeen
 * 作者：李志超 on 2017/3/23 18:57
 */

public class RechargeBeen {
    private String lmorder_id;
    private String order_sn;
    private String uid;
    private String amount;
    private String order_price;
    private String pay_status;
    private String pay_type;
    private String pay_time;
    private String create_time;

    public String getLmorder_id() {
        return lmorder_id;
    }

    public void setLmorder_id(String lmorder_id) {
        this.lmorder_id = lmorder_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
