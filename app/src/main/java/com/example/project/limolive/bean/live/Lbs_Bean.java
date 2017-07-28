package com.example.project.limolive.bean.live;

/**
 * 作者：hpg on 2016/12/29 09:44
 * 功能：
 */
public class Lbs_Bean {

    private String longitude;
    private String latitude;
    private String address;

    public Lbs_Bean() {
    }

    public Lbs_Bean(String longitude, String latitude, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Lbs_Bean{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
