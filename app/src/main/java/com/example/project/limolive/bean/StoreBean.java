package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/30.
 */

public class StoreBean {
    /**
     *  "nickname": "我是神",
     "headsmall": "\/data\/upload\/user\/user_4\/5992c33306659.png",
     "focus_num": "0",
     "waitpay": "2",
     "waitsend": "0",
     "today_order": "0",
     "today_order_money": "0",
     "sales_sum": "0",
     "refunding": "0",
     "is_sales": "0"
     */
    private String nickname;
    private String headsmall;
    private String focus_num;
    private String waitpay;
    private String waitsend;
    private String today_order;
    private String today_order_money;
    private String sales_sum;
    private String refunding;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getFocus_num() {
        return focus_num;
    }

    public void setFocus_num(String focus_num) {
        this.focus_num = focus_num;
    }

    public String getWaitpay() {
        return waitpay;
    }

    public void setWaitpay(String waitpay) {
        this.waitpay = waitpay;
    }

    public String getWaitsend() {
        return waitsend;
    }

    public void setWaitsend(String waitsend) {
        this.waitsend = waitsend;
    }

    public String getToday_order() {
        return today_order;
    }

    public void setToday_order(String today_order) {
        this.today_order = today_order;
    }

    public String getToday_order_money() {
        return today_order_money;
    }

    public void setToday_order_money(String today_order_money) {
        this.today_order_money = today_order_money;
    }

    public String getSales_sum() {
        return sales_sum;
    }

    public void setSales_sum(String sales_sum) {
        this.sales_sum = sales_sum;
    }

    public String getRefunding() {
        return refunding;
    }

    public void setRefunding(String refunding) {
        this.refunding = refunding;
    }

    public String getIs_sales() {
        return is_sales;
    }

    public void setIs_sales(String is_sales) {
        this.is_sales = is_sales;
    }

    private String is_sales;

    @Override
    public String toString() {
        return "StoreBean{" +
                "nickname='" + nickname + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", focus_num='" + focus_num + '\'' +
                ", waitpay='" + waitpay + '\'' +
                ", waitsend='" + waitsend + '\'' +
                ", today_order='" + today_order + '\'' +
                ", today_order_money='" + today_order_money + '\'' +
                ", sales_sum='" + sales_sum + '\'' +
                ", refunding='" + refunding + '\'' +
                ", is_sales='" + is_sales + '\'' +
                '}';
    }
}
