package com.example.project.limolive.bean.live;

/**
 * 作者：hpg on 2016/12/29 09:36
 * 功能：在直播的2个（更多）好友店铺页面
 */
public class Be_Liveing_Bean {

    private String avRoomId;        //	直播房间号码
    private String chatRoomId;      //	聊天室ID
    private String title;           //	直播标题
    private String cover;           //	直播房间封面图
    private HostBean host;            //  主播信息
    private String avatar;          //	直播头像
    private String username;        //	直播昵称
    private Lbs_Bean lbs;       //	地理位置信息
    private String longitude;       //	经度
    private String latitude;        //  纬度
    private String address;         //  地址
    private String timeSpan;        //  直播时长
    private String watchCount;      //  观看人数
    private String admireCount;     //  点赞人数
    private String createTime;      //  创建时间

    public Be_Liveing_Bean() {
    }

    public Be_Liveing_Bean(String avRoomId, String chatRoomId, String title, String cover, HostBean host, String avatar, String username,
                           Lbs_Bean lbs, String longitude,
                           String latitude, String address, String timeSpan, String watchCount, String admireCount, String createTime) {
        this.avRoomId = avRoomId;
        this.chatRoomId = chatRoomId;
        this.title = title;
        this.cover = cover;
        this.host = host;
        this.avatar = avatar;
        this.username = username;
        this.lbs = lbs;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.timeSpan = timeSpan;
        this.watchCount = watchCount;
        this.admireCount = admireCount;
        this.createTime = createTime;
    }

    public String getAvRoomId() {
        return avRoomId;
    }

    public void setAvRoomId(String avRoomId) {
        this.avRoomId = avRoomId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public HostBean getHost() {
        return host;
    }

    public void setHost(HostBean host) {
        this.host = host;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Lbs_Bean getLbs() {
        return lbs;
    }

    public void setLbs(Lbs_Bean lbs) {
        this.lbs = lbs;
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

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(String watchCount) {
        this.watchCount = watchCount;
    }

    public String getAdmireCount() {
        return admireCount;
    }

    public void setAdmireCount(String admireCount) {
        this.admireCount = admireCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Be_Liveing_Bean{" +
                "avRoomId='" + avRoomId + '\'' +
                ", chatRoomId='" + chatRoomId + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", host=" + host +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", lbs=" + lbs +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", timeSpan='" + timeSpan + '\'' +
                ", watchCount='" + watchCount + '\'' +
                ", admireCount='" + admireCount + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
