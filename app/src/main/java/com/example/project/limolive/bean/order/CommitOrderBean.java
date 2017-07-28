package com.example.project.limolive.bean.order;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/24.
 */

public class CommitOrderBean implements Serializable {
    private AddressList addressList;
    private CartList cartList;
    private String totalPrice;

    public class AddressList implements Serializable {
        private String address;
        private String address_id;
        private String city;
        private String city_name;
        private String consignee;
        private String country;
        private String country_name;
        private String district;
        private String district_name;
        private String email;
        private String is_default;
        private String is_pickup;
        private String mobile;
        private String province;
        private String province_name;
        private String twon;
        private String twon_name;
        private String user_id;
        private String zipcode;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
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

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
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

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public String getIs_pickup() {
            return is_pickup;
        }

        public void setIs_pickup(String is_pickup) {
            this.is_pickup = is_pickup;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }
    }

    public class CartList implements Serializable {
        private String cat_id;
        private String click_count;
        private String comment_count;
        private String goods_content;
        private String goods_id;
        private String goods_name;
        private String goods_num;
        private String goods_remark;
        private String goods_type;
        private String is_checked;
        private String is_free_shipping;
        private String is_on_sale;
        private String is_real;
        private String last_update;
        private String on_time;
        private String original_img;
        private String prom_id;
        private String prom_type;
        private String sales_sum;
        private String shiping_price;
        private String shop_price;
        private String store_count;
        private String store_name;
        private String uid;
        private String weight;

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getClick_count() {
            return click_count;
        }

        public void setClick_count(String click_count) {
            this.click_count = click_count;
        }

        public String getComment_count() {
            return comment_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public String getGoods_content() {
            return goods_content;
        }

        public void setGoods_content(String goods_content) {
            this.goods_content = goods_content;
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

        public String getGoods_remark() {
            return goods_remark;
        }

        public void setGoods_remark(String goods_remark) {
            this.goods_remark = goods_remark;
        }

        public String getGoods_type() {
            return goods_type;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public String getIs_checked() {
            return is_checked;
        }

        public void setIs_checked(String is_checked) {
            this.is_checked = is_checked;
        }

        public String getIs_free_shipping() {
            return is_free_shipping;
        }

        public void setIs_free_shipping(String is_free_shipping) {
            this.is_free_shipping = is_free_shipping;
        }

        public String getIs_on_sale() {
            return is_on_sale;
        }

        public void setIs_on_sale(String is_on_sale) {
            this.is_on_sale = is_on_sale;
        }

        public String getIs_real() {
            return is_real;
        }

        public void setIs_real(String is_real) {
            this.is_real = is_real;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public String getOn_time() {
            return on_time;
        }

        public void setOn_time(String on_time) {
            this.on_time = on_time;
        }

        public String getOriginal_img() {
            return original_img;
        }

        public void setOriginal_img(String original_img) {
            this.original_img = original_img;
        }

        public String getProm_id() {
            return prom_id;
        }

        public void setProm_id(String prom_id) {
            this.prom_id = prom_id;
        }

        public String getProm_type() {
            return prom_type;
        }

        public void setProm_type(String prom_type) {
            this.prom_type = prom_type;
        }

        public String getSales_sum() {
            return sales_sum;
        }

        public void setSales_sum(String sales_sum) {
            this.sales_sum = sales_sum;
        }

        public String getShiping_price() {
            return shiping_price;
        }

        public void setShiping_price(String shiping_price) {
            this.shiping_price = shiping_price;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }

        public String getStore_count() {
            return store_count;
        }

        public void setStore_count(String store_count) {
            this.store_count = store_count;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }
    }

    public AddressList getAddressList() {
        return addressList;
    }

    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
    }

    public CartList getCartList() {
        return cartList;
    }

    public void setCartList(CartList cartList) {
        this.cartList = cartList;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
