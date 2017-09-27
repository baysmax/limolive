package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/9/27.
 */

public class RechargeLiveBean {
    /**
     * 返回参数	uid（用户id）
     * 、order_sn（订单编号）
     * 、room_id（房间号id）
     * 、amount（充值数量）
     * 、order_price（充值金额）
     * 、pay_status（支付状态）
     * 、pay_type（支付方式）
     * 、pay_time（支付时间）
     * 、create_time（创建时间）
     */
    private String uid;
    private String order_sn;
    private String room_id;
    private String amount;
    private String order_price;
    private String pay_status;
    private String pay_type;
    private String pay_time;
    private String create_time;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
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

    @Override
    public String toString() {
        return "RechargeLiveBean{" +
                "uid='" + uid + '\'' +
                ", order_sn='" + order_sn + '\'' +
                ", room_id='" + room_id + '\'' +
                ", amount='" + amount + '\'' +
                ", order_price='" + order_price + '\'' +
                ", pay_status='" + pay_status + '\'' +
                ", pay_type='" + pay_type + '\'' +
                ", pay_time='" + pay_time + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
