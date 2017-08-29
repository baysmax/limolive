package com.example.project.limolive.bean.order;

import java.util.List;

/**
 * Created by ZL on 2017/2/23.
 */

public class OrderBean {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String add_time;
    private String address;
    private String admin_note;
    private String cancel_btn;
    private String city;
    private String city_name;
    private String comment_btn;
    private String confirm_time;
    private String consignee;
    private String country;
    private String coupon_price;
    private String discount;
    private String district;
    private String district_name;
    private String email;
    private List<Goods> goods_list;
    private String goods_price;
    private String integral;
    private String integral_money;
    private String is_distribut;
    private String mobile;
    private String order_amount;
    private String order_id;
    private String order_prom_amount;
    private String order_prom_id;
    private String order_sn;
    private String order_status;
    private String order_status_code;
    private String order_status_desc;
    private String order_status_type;
    private String pay_btn;
    private String pay_code;
    private String pay_name;
    private String pay_status;
    private String pay_time;
    private String province;
    private String province_name;
    private String receive_btn;
    private String return_btn;
    private String shipping_btn;
    private String shipping_code;
    private String shipping_name;
    private String shipping_price;
    private String shipping_status;
    private String shipping_time;
    private String store_name;
    private String total_amount;
    private String twon;
    private String twon_name;
    private String uid;
    private String user_id;
    private String user_money;
    private String user_note;
    private String zipcode;

    public class Goods {

        private String bar_code;
        private String delivery_id;
        private String goods_id;
        private String goods_name;
        private String goods_num;
        private String goods_price;
        private String is_comment;
        private String is_send;
        private String order_id;
        private String original_img;
        private String prom_type;
        private String rec_id;
        private String remark;
        private String sku;

        public String getGood_standard_size() {
            return good_standard_size;
        }

        public void setGood_standard_size(String good_standard_size) {
            this.good_standard_size = good_standard_size;
        }

        private String good_standard_size;

        public String getBar_code() {
            return bar_code;
        }

        public void setBar_code(String bar_code) {
            this.bar_code = bar_code;
        }

        public String getDelivery_id() {
            return delivery_id;
        }

        public void setDelivery_id(String delivery_id) {
            this.delivery_id = delivery_id;
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

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getIs_comment() {
            return is_comment;
        }

        public void setIs_comment(String is_comment) {
            this.is_comment = is_comment;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getIs_send() {
            return is_send;
        }

        public void setIs_send(String is_send) {
            this.is_send = is_send;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public String getProm_type() {
            return prom_type;
        }

        public void setProm_type(String prom_type) {
            this.prom_type = prom_type;
        }

        public String getRec_id() {
            return rec_id;
        }

        public void setRec_id(String rec_id) {
            this.rec_id = rec_id;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdmin_note() {
        return admin_note;
    }

    public void setAdmin_note(String admin_note) {
        this.admin_note = admin_note;
    }

    public String getCancel_btn() {
        return cancel_btn;
    }

    public void setCancel_btn(String cancel_btn) {
        this.cancel_btn = cancel_btn;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getComment_btn() {
        return comment_btn;
    }

    public void setComment_btn(String comment_btn) {
        this.comment_btn = comment_btn;
    }

    public String getConfirm_time() {
        return confirm_time;
    }

    public void setConfirm_time(String confirm_time) {
        this.confirm_time = confirm_time;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(String coupon_price) {
        this.coupon_price = coupon_price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Goods> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<Goods> goods_list) {
        this.goods_list = goods_list;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIntegral_money() {
        return integral_money;
    }

    public void setIntegral_money(String integral_money) {
        this.integral_money = integral_money;
    }

    public String getIs_distribut() {
        return is_distribut;
    }

    public void setIs_distribut(String is_distribut) {
        this.is_distribut = is_distribut;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_prom_amount() {
        return order_prom_amount;
    }

    public void setOrder_prom_amount(String order_prom_amount) {
        this.order_prom_amount = order_prom_amount;
    }

    public String getOrder_prom_id() {
        return order_prom_id;
    }

    public void setOrder_prom_id(String order_prom_id) {
        this.order_prom_id = order_prom_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_status_code() {
        return order_status_code;
    }

    public void setOrder_status_code(String order_status_code) {
        this.order_status_code = order_status_code;
    }

    public String getOrder_status_desc() {
        return order_status_desc;
    }

    public void setOrder_status_desc(String order_status_desc) {
        this.order_status_desc = order_status_desc;
    }

    public String getPay_btn() {
        return pay_btn;
    }

    public void setPay_btn(String pay_btn) {
        this.pay_btn = pay_btn;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public String getReceive_btn() {
        return receive_btn;
    }

    public void setReceive_btn(String receive_btn) {
        this.receive_btn = receive_btn;
    }

    public String getReturn_btn() {
        return return_btn;
    }

    public void setReturn_btn(String return_btn) {
        this.return_btn = return_btn;
    }

    public String getShipping_btn() {
        return shipping_btn;
    }

    public void setShipping_btn(String shipping_btn) {
        this.shipping_btn = shipping_btn;
    }

    public String getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(String shipping_code) {
        this.shipping_code = shipping_code;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_price() {
        return shipping_price;
    }

    public void setShipping_price(String shipping_price) {
        this.shipping_price = shipping_price;
    }

    public String getShipping_status() {
        return shipping_status;
    }

    public void setShipping_status(String shipping_status) {
        this.shipping_status = shipping_status;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getTwon() {
        return twon;
    }

    public void setTwon(String twon) {
        this.twon = twon;
    }

    public String getTwon_name() {
        return twon_name;
    }

    public void setTwon_name(String twon_name) {
        this.twon_name = twon_name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getUser_note() {
        return user_note;
    }

    public void setUser_note(String user_note) {
        this.user_note = user_note;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getOrder_status_type() {
        return order_status_type;
    }

    public void setOrder_status_type(String order_status_type) {
        this.order_status_type = order_status_type;
    }
}
