package com.example.project.limolive.bean.live;

/**
 * 作者：hpg on 2016/12/29 09:40
 * 功能：
 */
public class HostBean {

    private String avatar;
    private String username;
    private String uid;
    private String phone;

    public HostBean() {
    }

    public HostBean(String avatar, String username, String uid, String phone) {
        this.avatar = avatar;
        this.username = username;
        this.uid = uid;
        this.phone = phone;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "HostBean{" +
                "avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
