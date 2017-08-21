package com.example.project.limolive.bean;

/**
 * 作者：hpg on 2017/3/17 18:58
 * 功能：
 */
public class ThridLogin {
    private String uid;
    private String phone;
    private String nickname;
    private String livenum;
    private String headsmall;
    private String sex;
    private String personalized;
    private String birthday;
    private String love_status;
    private String province;
    private String city;
    private String job;
    private String verify;
    private String real_name;
    private String idcard;
    private String login_type;
    private String openid;
    private String create_time;

    public String getUser_robot() {
        return user_robot;
    }

    public void setUser_robot(String user_robot) {
        this.user_robot = user_robot;
    }

    private String user_robot;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPersonalized() {
        return personalized;
    }

    public void setPersonalized(String personalized) {
        this.personalized = personalized;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getLove_status() {
        return love_status;
    }

    public void setLove_status(String love_status) {
        this.love_status = love_status;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "ThridLogin{" +
                "uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", nickname='" + nickname + '\'' +
                ", livenum='" + livenum + '\'' +
                ", headsmall='" + headsmall + '\'' +
                ", sex='" + sex + '\'' +
                ", personalized='" + personalized + '\'' +
                ", birthday='" + birthday + '\'' +
                ", love_status='" + love_status + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", job='" + job + '\'' +
                ", verify='" + verify + '\'' +
                ", real_name='" + real_name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", login_type='" + login_type + '\'' +
                ", openid='" + openid + '\'' +
                ", create_time='" + create_time + '\'' +
                ", user_robot='" + user_robot + '\'' +
                '}';
    }
}
