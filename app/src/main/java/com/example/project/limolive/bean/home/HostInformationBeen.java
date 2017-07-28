package com.example.project.limolive.bean.home;
/**
 * Created by Administrator on 2016/12/14.
 */

/**
 * HostInformationBeen
 * 作者：李志超 on 2016/12/14
 */

public class HostInformationBeen {

    //直播头像
    private String avatar;
    //直播手机号
    private String phone;
    private String uid;
    //直播昵称
    private String username;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "HostInformationBeen{" +
                "avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
