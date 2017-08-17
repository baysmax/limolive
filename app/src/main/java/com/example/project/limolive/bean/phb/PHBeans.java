package com.example.project.limolive.bean.phb;

/**
 * Created by AAA on 2017/8/2.
 */

public class PHBeans {
    private String uid;//用户id
    private String nickname;//昵称
    private String headsmall;//头像
    private String phone;
    private String charm;//柠檬币
    private String follow;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCharm() {
        return charm;
    }

    public void setCharm(String charm) {
        this.charm = charm;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }



    public PHBeans() {
    }

    public PHBeans(String uid, String nickname, String headsmall, String phone, String charm, String follow) {
        this.uid = uid;
        this.nickname = nickname;
        this.headsmall = headsmall;
        this.phone = phone;
        this.charm = charm;
        this.follow = follow;
    }

    @Override
    public String toString() {
        return "PHBeans{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", phone='" + phone + '\'' +
                ", charm='" + charm + '\'' +
                ", follow='" + follow + '\'' +
                '}';
    }
}
