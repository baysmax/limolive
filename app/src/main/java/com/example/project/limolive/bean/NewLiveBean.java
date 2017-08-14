package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/14.
 */

public class NewLiveBean {
    private String img="";
    private String id;
    private String nick;
    private boolean isLive;
    private String addess;

    public NewLiveBean(String img, String id, String nick, boolean isLive, String addess) {
        this.img = img;
        this.id = id;
        this.nick = nick;
        this.isLive = isLive;
        this.addess = addess;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public String getAddess() {
        return addess;
    }

    public void setAddess(String addess) {
        this.addess = addess;
    }

    @Override
    public String toString() {
        return "NewLiveBean{" +
                "img='" + img + '\'' +
                ", id='" + id + '\'' +
                ", nick='" + nick + '\'' +
                ", isLive=" + isLive +
                ", addess='" + addess + '\'' +
                '}';
    }
}
