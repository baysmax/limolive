package com.example.project.limolive.tencentim.model;

import java.io.Serializable;

/**
 * 作者：hpg on 2016/12/27 11:04
 * 功能：
 */
public class FriendInfoBean implements Serializable{

    private String uid;
    private String birthday;
    private String personalized;
    private String sex;
    private String phone;
    private String nickname;
    private String verify;
    private String idcard;
    private String job;
    private String password;
    private String livenum;
    private String city;
    private String headsmall;
    private String real_name;
    private String create_time;
    private String province;
    private String money;
    private String love_status;
    private String store_name;

    private String letter = ""; //字母，转为拼音后在这里添加

    public FriendInfoBean(String phone) {
        this.phone = phone;
    }

    public FriendInfoBean() {
    }

    public FriendInfoBean(String uid, String birthday, String personalized, String sex, String phone, String nickname, String verify, String idcard, String job,
                          String password, String livenum, String city, String headsmall,
                          String real_name, String create_time, String province, String money, String love_status, String store_name, String letter) {
        this.uid = uid;
        this.birthday = birthday;
        this.personalized = personalized;
        this.sex = sex;
        this.phone = phone;
        this.nickname = nickname;
        this.verify = verify;
        this.idcard = idcard;
        this.job = job;
        this.password = password;
        this.livenum = livenum;
        this.city = city;
        this.headsmall = headsmall;
        this.real_name = real_name;
        this.create_time = create_time;
        this.province = province;
        this.money = money;
        this.love_status = love_status;
        this.store_name = store_name;
        this.letter = letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FriendInfoBean)) return false;

        FriendInfoBean that = (FriendInfoBean) o;

        return phone != null ? phone.equals(that.phone) : that.phone == null;

    }

    @Override
    public int hashCode() {
        return phone != null ? phone.hashCode() : 0;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPersonalized() {
        return personalized;
    }

    public void setPersonalized(String personalized) {
        this.personalized = personalized;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLivenum() {
        return livenum;
    }

    public void setLivenum(String livenum) {
        this.livenum = livenum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getLove_status() {
        return love_status;
    }

    public void setLove_status(String love_status) {
        this.love_status = love_status;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }


    @Override
    public String toString() {
        return "FriendInfoBean{" +
                "uid='" + uid + '\'' +
                ", birthday='" + birthday + '\'' +
                ", personalized='" + personalized + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", verify='" + verify + '\'' +
                ", idcard='" + idcard + '\'' +
                ", job='" + job + '\'' +
                ", password='" + password + '\'' +
                ", livenum='" + livenum + '\'' +
                ", city='" + city + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", real_name='" + real_name + '\'' +
                ", create_time='" + create_time + '\'' +
                ", province='" + province + '\'' +
                ", money='" + money + '\'' +
                ", love_status='" + love_status + '\'' +
                ", store_name='" + store_name + '\'' +
                ", letter='" + letter + '\'' +
                '}';
    }
}
