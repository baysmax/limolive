package com.example.project.limolive.bean.mine;

/**
 * 作者：黄亚菲 on 2017/2/24 16:03
 * 功能：黑名单
 */
public class BlackListBean {

//    "uid": "23",
//            "phone": "15727366538",
//            "nickname": "yanghui",
//            "headsmall": ""

    private String uid;
    private String phone;
    private String nickname;
    private String headsmall;

    public BlackListBean(String uid, String phone, String nickname, String headsmall) {
        this.uid = uid;
        this.phone = phone;
        this.nickname = nickname;
        this.headsmall = headsmall;
    }

    public BlackListBean() {
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

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    @Override
    public String toString() {
        return "BlackListBean{" +
                "uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headsmall='" + headsmall + '\'' +
                '}';
    }
}
