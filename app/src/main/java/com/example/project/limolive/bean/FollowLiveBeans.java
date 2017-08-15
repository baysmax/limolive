package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/14.
 */

public class FollowLiveBeans {
    private String user_id;
    private String nick;
    private String mlz;
    private String number;
    private String largeImgs;
    private String avatar;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    private boolean isLive;

    public FollowLiveBeans(String user_id, String nick, String mlz, String number, String largeImgs, String avatar, boolean isLive) {
        this.user_id = user_id;
        this.nick = nick;
        this.mlz = mlz;
        this.number = number;
        this.largeImgs = largeImgs;
        this.avatar = avatar;
        this.isLive = isLive;
    }
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMlz() {
        return mlz;
    }

    public void setMlz(String mlz) {
        this.mlz = mlz;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLargeImgs() {
        return largeImgs;
    }

    public void setLargeImgs(String largeImgs) {
        this.largeImgs = largeImgs;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public String toString() {
        return "FollowLiveBeans{" +
                "user_id='" + user_id + '\'' +
                ", nick='" + nick + '\'' +
                ", mlz='" + mlz + '\'' +
                ", number='" + number + '\'' +
                ", largeImgs='" + largeImgs + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isLive=" + isLive +
                '}';
    }
}
