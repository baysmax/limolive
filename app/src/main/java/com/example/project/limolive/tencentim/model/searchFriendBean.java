package com.example.project.limolive.tencentim.model;

/**
 * 作者：hpg on 2017/1/6 11:01
 * 功能：搜索添加好友  信息bean
 */
public class searchFriendBean {
    private String uid; //	用户UID
    private String phone;     //	用户手机号码
    private String nickname;     //	用户昵称
    private String is_live; //	是否在直播 1是，0否
    private String headsmall; //	用户头像地址
    private String personalized;//个性签名
    private String is_friend;//是否为好友 1是0否
    public searchFriendBean() {
    }

    public searchFriendBean(String uid, String phone, String nickname, String is_live, String headsmall,String personalized,String is_friend) {
        this.uid = uid;
        this.phone = phone;
        this.nickname = nickname;
        this.is_live = is_live;
        this.headsmall = headsmall;
        this.personalized = personalized;
        this.is_friend = is_friend;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getPersonalized() {
        return personalized;
    }

    public void setPersonalized(String personalized) {
        this.personalized = personalized;
    }

    public String getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(String is_friend) {
        this.is_friend = is_friend;
    }

    @Override
    public String toString() {
        return "searchFriendBean{" +
                "uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", is_live='" + is_live + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", personalized='" + personalized + '\'' +
                ", is_friend='" + is_friend + '\'' +
                '}';
    }
}
