package com.example.project.limolive.tencentlive.model;

/**
 * 互动直播成员
 */
public class AvMemberInfo {

    private String uid;             //  用户UID
    private String phone;           //  用户手机号码
    private String nickname;        // 	用户昵称
    private String livenum;         //	用户直播号
    private String headsmall;       // 	用户头像地址
    private String sex;             //  用户性别 0未填写 1男 2女
    private String personalized;    // 	个性签名
    private String birthday;        //	出生日期 时间戳
    private String love_status;     //  情感状态 0保密 1单身 2恋爱中 3已婚
    private String province;        //	用户身份
    private String city;            //	用户城市
    private String job;             //  工作
    private String verify;          //  是否认证 0未认证 1认证中 2通过认证 3认证失败
    private String real_name;       // 	真实姓名
    private String idcard;          //  身份证号码
    private String create_time;     //  注册时间
    private String store_name;      //  商店名字

    public AvMemberInfo() {
    }

    public AvMemberInfo(String uid, String phone, String nickname, String livenum, String headsmall, String sex, String personalized, String birthday,
                        String love_status, String province, String city, String job, String verify, String real_name,
                        String idcard, String create_time, String store_name) {
        this.uid = uid;
        this.phone = phone;
        this.nickname = nickname;
        this.livenum = livenum;
        this.headsmall = headsmall;
        this.sex = sex;
        this.personalized = personalized;
        this.birthday = birthday;
        this.love_status = love_status;
        this.province = province;
        this.city = city;
        this.job = job;
        this.verify = verify;
        this.real_name = real_name;
        this.idcard = idcard;
        this.create_time = create_time;
        this.store_name = store_name;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    @Override
    public String toString() {
        return "AvMemberInfo{" +
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
                ", create_time='" + create_time + '\'' +
                ", store_name='" + store_name + '\'' +
                '}';
    }
}