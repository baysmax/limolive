package com.example.project.limolive.baidu_location.model;


/**
 * Created by lzc on 2017/1/6.
 */

public class SearchModel {

    //用户id
    private String uid;

    //纬度
    private double latitude;

    //经度
    private double longitude;

    //页数
    private int page;

    //搜索经纬度
    private String mGps;

    //搜索类型
    private int mSearchType ;

    //表名
    private int mTableId;

    //半径
    private int mRadius;

    //层级
    private float mLevel = 100;


    public String getGps() {
        return mGps;
    }

    public void setGps(String gps) {
        this.mGps = gps;
    }

    public int getSearchType() {
        return mSearchType;
    }

    public void setSearchType(int searchType) {
        this.mSearchType = searchType;
    }

    public int getTableId() {
        return mTableId;
    }

    public void setTableId(int tableId) {
        this.mTableId = tableId;
    }

    public float getLevel() {
        return mLevel;
    }

    public void setLevel(float level) {
        this.mLevel = level;
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public float getmLevel() {
        return mLevel;
    }

    public void setmLevel(float mLevel) {
        this.mLevel = mLevel;
    }
}
