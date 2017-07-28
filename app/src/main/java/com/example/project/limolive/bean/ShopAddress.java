package com.example.project.limolive.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 收货地址
 * @author hwj on 2016/12/20.
 */

public class ShopAddress implements Parcelable{

    private String address_id;
    private String user_id;
    private String consignee;
    private String email;
    private String country;
    private String country_name;
    private String province;
    private String province_name;
    private String city;
    private String city_name;
    private String district;
    private String district_name;
    private String twon;
    private String twon_name;
    private String address;
    private String zipcode;
    private String mobile;
    private String is_default;
    private String is_pickup;

    public ShopAddress(){}

    protected ShopAddress(Parcel in) {
        address_id = in.readString();
        user_id = in.readString();
        consignee = in.readString();
        email = in.readString();
        country = in.readString();
        country_name = in.readString();
        province = in.readString();
        province_name = in.readString();
        city = in.readString();
        city_name = in.readString();
        district = in.readString();
        district_name = in.readString();
        twon = in.readString();
        twon_name = in.readString();
        address = in.readString();
        zipcode = in.readString();
        mobile = in.readString();
        is_default = in.readString();
        is_pickup = in.readString();
    }

    public static final Creator<ShopAddress> CREATOR = new Creator<ShopAddress>() {
        @Override
        public ShopAddress createFromParcel(Parcel in) {
            return new ShopAddress(in);
        }

        @Override
        public ShopAddress[] newArray(int size) {
            return new ShopAddress[size];
        }
    };

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address_id);
        dest.writeString(user_id);
        dest.writeString(consignee);
        dest.writeString(email);
        dest.writeString(country);
        dest.writeString(country_name);
        dest.writeString(province);
        dest.writeString(province_name);
        dest.writeString(city);
        dest.writeString(city_name);
        dest.writeString(district);
        dest.writeString(district_name);
        dest.writeString(twon);
        dest.writeString(twon_name);
        dest.writeString(address);
        dest.writeString(zipcode);
        dest.writeString(mobile);
        dest.writeString(is_default);
        dest.writeString(is_pickup);
    }
}
