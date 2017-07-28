package com.example.project.limolive.bean.home;
/**
 * Created by Administrator on 2016/12/14.
 */

/**
 * AdressInformationBeen
 * 作者：李志超 on 2016/12/14
 */

public class AdressInformationBeen {

    //地区
    private String address;
    //纬度
    private String latitude;
    //经度
    private String longitude;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "AdressInformationBeen{" +
                "address='" + address + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
