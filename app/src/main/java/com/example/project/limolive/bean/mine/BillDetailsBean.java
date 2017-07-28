package com.example.project.limolive.bean.mine;

/**
 * 作者：黄亚菲 on 2017/2/24 15:22
 * 功能：账单明细
 */
public class BillDetailsBean {

//    "pay_name": "手机网站支付宝",
//            "total_amount": "68.00",
//            "pay_time": "1478275200"

    private String pay_name;
    private String total_amount;
    private String pay_time;

    public BillDetailsBean(String pay_name, String total_amount, String pay_time) {
        this.pay_name = pay_name;
        this.total_amount = total_amount;
        this.pay_time = pay_time;
    }

    public BillDetailsBean() {
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    @Override
    public String toString() {
        return "BillDetailsBean{" +
                "pay_name='" + pay_name + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", pay_time='" + pay_time + '\'' +
                '}';
    }
}
