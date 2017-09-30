package com.example.project.limolive.tencentlive.model;


/**
 * 当前直播信息页面
 */
public class CurLiveInfo {
    private static int members;

    public static void setMaxmembers(int maxmembers) {
        CurLiveInfo.maxmembers = maxmembers;
    }

    private static int maxmembers;//最大人数
    private static int admires;

    private static String title;
    private static double lat1;
    private static double long1;
    private static String address = "";
    private static String coverurl = "";//背景图
    private static String liveType = "";//背景图

    public static String roomNum;

    public static String hostID;
    public static String hostName;
    public static String hostAvator;
    public static String host_phone;
    public static int currentRequestCount = 0;

    public static int getCurrentRequestCount() {
        return currentRequestCount;
    }

    public static int getIndexView() {
        return indexView;
    }

    public static void setIndexView(int indexView) {
        CurLiveInfo.indexView = indexView;
    }

    public static int indexView = 0;

    public static String getHost_phone() {
        return host_phone;
    }

    public static void setHost_phone(String host_phone) {
        CurLiveInfo.host_phone = host_phone;
    }

    public static void setCurrentRequestCount(int currentRequestCount) {
        CurLiveInfo.currentRequestCount = currentRequestCount;
    }

    public static String getLiveType() {
        return liveType;
    }

    public static void setLiveType(String liveType) {
        CurLiveInfo.liveType = liveType;
    }

    public static String getHostID() {
        return hostID;
    }

    public static void setHostID(String hostID) {
        CurLiveInfo.hostID = hostID;
    }

    public static String getHostName() {
        return hostName;
    }

    public static void setHostName(String hostName) {
        CurLiveInfo.hostName = hostName;
    }

    public static String getHostAvator() {
        return hostAvator;
    }

    public static void setHostAvator(String hostAvator) {
        CurLiveInfo.hostAvator = hostAvator;
    }

    public static int getMembers() {
        return members;
    }

    public static void setMembers(int members) {
        if (members<=0){
            CurLiveInfo.members=0;
        }else {
            CurLiveInfo.members = members;
            if (CurLiveInfo.members>CurLiveInfo.maxmembers){
                CurLiveInfo.maxmembers=CurLiveInfo.members;
            }
        }

    }

    public static int getAdmires() {
        return admires;
    }

    public static void setAdmires(int admires) {
        CurLiveInfo.admires = admires;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        CurLiveInfo.title = title;
    }

    public static double getLat1() {
        return lat1;
    }

    public static void setLat1(double lat1) {
        CurLiveInfo.lat1 = lat1;
    }

    public static double getLong1() {
        return long1;
    }

    public static void setLong1(double long1) {
        CurLiveInfo.long1 = long1;
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        CurLiveInfo.address = address;
    }

    public static String getRoomNum() {
        return roomNum;
    }

    public static void setRoomNum(String roomNum) {
        CurLiveInfo.roomNum = roomNum;
    }

    public static String getCoverurl() {
        return coverurl;
    }

    public static void setCoverurl(String coverurl) {
        CurLiveInfo.coverurl = coverurl;
    }

    public static String getChatRoomId() {
        return "" + roomNum;
    }


    public static int getMaxMembers() {
        return maxmembers;
    }
}
