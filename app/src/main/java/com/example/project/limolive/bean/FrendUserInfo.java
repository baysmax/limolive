package com.example.project.limolive.bean;

/**
 * 作者：hpg on 2016/12/28 11:01
 * 功能：用户详情 查看朋友资料详情
 */
public class FrendUserInfo {
    private String uid;    //用户UID
    private String phone;        //用户手机号码
    private String nickname;     //	用户昵称
    private String livenum;     //	用户直播号
    private String headsmall;    // 	用户头像地址
    private String province;    // 	用户省份
    private String is_live;     //	是否在直播
    private String fans; //	粉丝数
    private String follows;     //	关注数

    public FrendUserInfo(String uid, String phone, String nickname, String livenum, String headsmall, String province, String is_live, String fans, String follows) {
        this.uid = uid;
        this.phone = phone;
        this.nickname = nickname;
        this.livenum = livenum;
        this.headsmall = headsmall;
        this.province = province;
        this.is_live = is_live;
        this.fans = fans;
        this.follows = follows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FrendUserInfo)) return false;

        FrendUserInfo that = (FrendUserInfo) o;

        return phone != null ? phone.equals(that.phone) : that.phone == null;

    }

    @Override
    public int hashCode() {
        return phone != null ? phone.hashCode() : 0;
    }

    public FrendUserInfo() {
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

    public String getLivenum() {
        return livenum;
    }

    public void setLivenum(String livenum) {
        this.livenum = livenum;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getIs_live() {
        return is_live;
    }

    public void setIs_live(String is_live) {
        this.is_live = is_live;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getFollows() {
        return follows;
    }

    public void setFollows(String follows) {
        this.follows = follows;
    }

    @Override
    public String toString() {
        return "FrendUserInfo{" +
                "uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", livenum='" + livenum + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", province='" + province + '\'' +
                ", is_live='" + is_live + '\'' +
                ", fans='" + fans + '\'' +
                ", follows='" + follows + '\'' +
                '}';
    }
}
