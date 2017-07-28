package com.example.project.limolive.baidu_location.event;

import com.example.project.limolive.baidu_location.ClusterBaiduItem;
import com.example.project.limolive.bean.home.HomeListBeen;

import java.util.List;
import java.util.Map;

public class RequestDataEvent {

    public static final int SUCCESS = 0;
    public static final int FAIL = 404;
    public static final int DEFAULT = 22;
    public static final int NULL = 26;

    private List<ClusterBaiduItem> mClusterBaiduItems;

    private List<HomeListBeen> mDataList;

    private Map<String, String> mParamsMap;

    private String mUUID;

    private String message;

    private int mEventType;

    private int mLastSize;

    private int mTotalSize;

    public RequestDataEvent() {

    }

    public List<ClusterBaiduItem> getClusterBaiduItems() {
        return mClusterBaiduItems;
    }

    public void setClusterBaiduItems(List<ClusterBaiduItem> clusterBaiduItems) {
        this.mClusterBaiduItems = clusterBaiduItems;
    }

    public List<HomeListBeen> getDataList() {
        return mDataList;
    }

    public void setDataList(List<HomeListBeen> dataList) {
        this.mDataList = dataList;
    }

    public Map<String, String> getParamsMap() {
        return mParamsMap;
    }

    public void setParamsMap(Map<String, String> paramsMap) {
        this.mParamsMap = paramsMap;
    }

    public String getUUID() {
        return mUUID;
    }

    public void setUUID(String UUID) {
        this.mUUID = UUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int eventType) {
        this.mEventType = eventType;
    }

    public int getLastSize() {
        return mLastSize;
    }

    public void setLastSize(int lastSize) {
        this.mLastSize = lastSize;
    }

    public int getTotalSize() {
        return mTotalSize;
    }

    public void setTotalSize(int totalSize) {
        this.mTotalSize = totalSize;
    }
}