package com.example.project.limolive.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */

public class CommitOrdersBean implements Serializable {
    private AddressList addressList;
    private List<CartList> cartList;
    private String totalPrice;

    public class AddressList  implements Serializable {
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

    public class CartList  implements Serializable {
        private List<Datas> data;
        private String goods_price;
        private String shiping_price;
        private String stores_name;
        private String uid;

        public class Datas implements Serializable {


            private String add_time;
            private String bar_code;
            private String goods_id;
            private String goods_name;
            private String goods_num;
            private String goods_price;
            private String id;
            private String member_goods_price;
            private String original_img;
            private String price_num;
            private String prom_id;
            private String prom_type;
            private String selected;
            private String shiping_price;
            private String sku;
            private String uid;
            private String user_id;

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getBar_code() {
                return bar_code;
            }

            public void setBar_code(String bar_code) {
                this.bar_code = bar_code;
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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMember_goods_price() {
                return member_goods_price;
            }

            public void setMember_goods_price(String member_goods_price) {
                this.member_goods_price = member_goods_price;
            }

            public String getOriginal_img() {
                return original_img;
            }

            public void setOriginal_img(String original_img) {
                this.original_img = original_img;
            }

            public String getPrice_num() {
                return price_num;
            }

            public void setPrice_num(String price_num) {
                this.price_num = price_num;
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

            public String getSelected() {
                return selected;
            }

            public void setSelected(String selected) {
                this.selected = selected;
            }

            public String getShiping_price() {
                return shiping_price;
            }

            public void setShiping_price(String shiping_price) {
                this.shiping_price = shiping_price;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
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
            @Override
            public String toString() {
                return "Datas{" +
                        "add_time='" + add_time + '\'' +
                        ", bar_code='" + bar_code + '\'' +
                        ", goods_id='" + goods_id + '\'' +
                        ", goods_name='" + goods_name + '\'' +
                        ", goods_num='" + goods_num + '\'' +
                        ", goods_price='" + goods_price + '\'' +
                        ", id='" + id + '\'' +
                        ", member_goods_price='" + member_goods_price + '\'' +
                        ", original_img='" + original_img + '\'' +
                        ", price_num='" + price_num + '\'' +
                        ", prom_id='" + prom_id + '\'' +
                        ", prom_type='" + prom_type + '\'' +
                        ", selected='" + selected + '\'' +
                        ", shiping_price='" + shiping_price + '\'' +
                        ", sku='" + sku + '\'' +
                        ", uid='" + uid + '\'' +
                        ", user_id='" + user_id + '\'' +
                        '}';
            }
        }

        public List<Datas> getData() {
            return data;
        }

        public void setData(List<Datas> data) {
            this.data = data;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getShiping_price() {
            return shiping_price;
        }

        public void setShiping_price(String shiping_price) {
            this.shiping_price = shiping_price;
        }

        public String getStores_name() {
            return stores_name;
        }

        public void setStores_name(String stores_name) {
            this.stores_name = stores_name;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public AddressList getAddressList() {
        return addressList;
    }

    public void setAddressList(AddressList addressList) {
        this.addressList = addressList;
    }

    public List<CartList> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartList> cartList) {
        this.cartList = cartList;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
