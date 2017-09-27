package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/9/27.
 */

public class LiveRechargeBean {
    /**
     * uid（用户id）、nickname（昵称）、headsmall（头像）、order_price（消费金额）
     */
    private String uid;
    private String nickname;
    private String headsmall;
    private String order_price;

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

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    @Override
    public String toString() {
        return "LiveRechargeBean{" +
                "uid='" + uid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", order_price='" + order_price + '\'' +
                '}';
    }
}
