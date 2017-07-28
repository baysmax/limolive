package com.example.project.limolive.tencentlive.model;

/**
 * 作者：hpg on 2016/12/15 13:10
 * 功能：
 */
public class RoomInfoBean {
    private String host_uid;        //主播UID
    private String host_phone;      //主播电话
    private String host_avatar;     //主播头像
    private String host_username;   //	string 	主播昵称
    private String longitude;       //	经度
    private String latitude;        //	纬度
    private String address;         //	地址
    private String av_room_id;      //直播间ID
    private String chat_room_id;    // 	聊天室ID
    private String create_time;     //	创建时间 时间戳
    private String cover;           //主播封面图
    private String live_type;       //	直播类型
    private String title;           //直播标题

    public RoomInfoBean() {
    }

    public RoomInfoBean(String host_uid, String host_phone, String host_avatar, String host_username,
                        String longitude, String latitude,
                        String address, String av_room_id, String chat_room_id, String create_time,
                        String cover, String live_type, String title) {
        this.host_uid = host_uid;
        this.host_phone = host_phone;
        this.host_avatar = host_avatar;
        this.host_username = host_username;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.av_room_id = av_room_id;
        this.chat_room_id = chat_room_id;
        this.create_time = create_time;
        this.cover = cover;
        this.live_type = live_type;
        this.title = title;
    }

    public String getHost_uid() {
        return host_uid;
    }

    public void setHost_uid(String host_uid) {
        this.host_uid = host_uid;
    }

    public String getHost_phone() {
        return host_phone;
    }

    public void setHost_phone(String host_phone) {
        this.host_phone = host_phone;
    }

    public String getHost_avatar() {
        return host_avatar;
    }

    public void setHost_avatar(String host_avatar) {
        this.host_avatar = host_avatar;
    }

    public String getHost_username() {
        return host_username;
    }

    public void setHost_username(String host_username) {
        this.host_username = host_username;
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

    public String getAv_room_id() {
        return av_room_id;
    }

    public void setAv_room_id(String av_room_id) {
        this.av_room_id = av_room_id;
    }

    public String getChat_room_id() {
        return chat_room_id;
    }

    public void setChat_room_id(String chat_room_id) {
        this.chat_room_id = chat_room_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getLive_type() {
        return live_type;
    }

    public void setLive_type(String live_type) {
        this.live_type = live_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RoomInfoBean{" +
                "host_uid='" + host_uid + '\'' +
                ", host_phone='" + host_phone + '\'' +
                ", host_avatar='" + host_avatar + '\'' +
                ", host_username='" + host_username + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", address='" + address + '\'' +
                ", av_room_id='" + av_room_id + '\'' +
                ", chat_room_id='" + chat_room_id + '\'' +
                ", create_time='" + create_time + '\'' +
                ", cover='" + cover + '\'' +
                ", live_type='" + live_type + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
