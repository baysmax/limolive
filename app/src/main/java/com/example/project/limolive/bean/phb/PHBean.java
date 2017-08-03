package com.example.project.limolive.bean.phb;

/**
 * Created by AAA on 2017/8/2.
 */

public class PHBean {
    private String uid;//用户id
    private String nickname;//昵称
    private String headsmall;//头像

    public PHBean(String uid, String nickname, String headsmall, String lemon_coins_sum, String mc) {
        this.uid = uid;
        this.nickname = nickname;
        this.headsmall = headsmall;
        this.lemon_coins_sum = lemon_coins_sum;
        this.mc = mc;
    }

    private String lemon_coins_sum;//刷柠檬币总和

    private String mc;

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
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

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getLemon_coins_sum() {
        return lemon_coins_sum;
    }

    public void setLemon_coins_sum(String lemon_coins_sum) {
        this.lemon_coins_sum = lemon_coins_sum;
    }

    public PHBean() {
    }


    @Override
    public String toString() {
        return "PHBean{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", lemon_coins_sum='" + lemon_coins_sum + '\'' +
                ", mc='" + mc + '\'' +
                '}';
    }
}
