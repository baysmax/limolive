package com.example.project.limolive.tencentim.model;

/**
 * 作者：hpg on 2016/12/27 18:48
 * 功能：
 */
public class SearchFrendInfoBean {
    private String uid;
    private String nickname;
    private String phone;
    private String headsmall;
    private String is_live;

    public SearchFrendInfoBean(String uid, String nickname, String phone, String headsmall, String is_live) {
        this.uid = uid;
        this.nickname = nickname;
        this.phone = phone;
        this.headsmall = headsmall;
        this.is_live = is_live;
    }

    public SearchFrendInfoBean() {
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    @Override
    public String toString() {
        return "SearchFrendInfoBean{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", is_live='" + is_live + '\'' +
                '}';
    }
}
