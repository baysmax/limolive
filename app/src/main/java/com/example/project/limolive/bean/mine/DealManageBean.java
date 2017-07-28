package com.example.project.limolive.bean.mine;

/**
 * 作者：黄亚菲 on 2017/2/23 11:47
 * 功能：交易管理
 */
public class DealManageBean {


    /*****
     * "waitpay": "0",
     * "waitsend": "0",
     * "waitreceive": "0",
     * "waitcomment": "1",
     * "refunding":"0",
     * "order_num": "1"
     */


    private String waitpay;
    private String waitsend;
    private String waitreceive;
    private String waitcomment;
    private String refunding;
    private String order_num;


    public DealManageBean(String waitpay, String waitsend, String waitreceive, String waitcomment, String refunding, String order_num) {
        this.waitpay = waitpay;
        this.waitsend = waitsend;
        this.waitreceive = waitreceive;
        this.waitcomment = waitcomment;
        this.refunding = refunding;
        this.order_num = order_num;
    }

    public DealManageBean() {
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

    public String getWaitreceive() {
        return waitreceive;
    }

    public void setWaitreceive(String waitreceive) {
        this.waitreceive = waitreceive;
    }

    public String getWaitcomment() {
        return waitcomment;
    }

    public void setWaitcomment(String waitcomment) {
        this.waitcomment = waitcomment;
    }

    public String getRefunding() {
        return refunding;
    }

    public void setRefunding(String refunding) {
        this.refunding = refunding;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }


    @Override
    public String toString() {
        return "DealManageBean{" +
                "waitpay='" + waitpay + '\'' +
                ", waitsend='" + waitsend + '\'' +
                ", waitreceive='" + waitreceive + '\'' +
                ", waitcomment='" + waitcomment + '\'' +
                ", refunding='" + refunding + '\'' +
                ", order_num='" + order_num + '\'' +
                '}';
    }
}
