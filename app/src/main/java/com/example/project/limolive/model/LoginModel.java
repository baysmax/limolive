package com.example.project.limolive.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author hwj on 2016/12/13.
 */

public class LoginModel implements Parcelable{

    private String uid;             //用户UID
    private String phone;           //用户手机号码
    private String password;
    private String nickname;        //用户昵称
    private String livenum;         //用户直播号
    private String headsmall;       //用户头像地址
    private String sex;             //用户性别 0未填写 1男 2女
    private String personalized;    //个性签名
    private String birthday;        //出生日期 时间戳
    private String love_status;     //情感状态 0保密 1单身 2恋爱中 3已婚
    private String money;
    private String province;        //用户身份
    private String city;            //用户城市
    private String job;             //工作
    private String verify;          //是否认证 0未认证 1认证中 2通过认证 3认证失败
    private String real_name;       //真实姓名
    private String idcard;          //身份证号码
    private String create_time;     //注册时间
    private String store_name;
    private String sig;


    public LoginModel(){}


    public LoginModel(String uid, String phone, String password, String nickname, String livenum,
                      String headsmall, String sex, String personalized,
                      String birthday, String love_status, String money,
                      String province, String city, String job,
                      String verify, String real_name, String idcard,
                      String create_time, String store_name, String sig) {
        this.uid = uid;
        this.phone = phone;
        this.password = password;
        this.nickname = nickname;
        this.livenum = livenum;
        this.headsmall = headsmall;
        this.sex = sex;
        this.personalized = personalized;
        this.birthday = birthday;
        this.love_status = love_status;
        this.money = money;
        this.province = province;
        this.city = city;
        this.job = job;
        this.verify = verify;
        this.real_name = real_name;
        this.idcard = idcard;
        this.create_time = create_time;
        this.store_name = store_name;
        this.sig = sig;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    protected LoginModel(Parcel in) {
        uid = in.readString();
        phone = in.readString();
        password = in.readString();
        nickname = in.readString();
        livenum = in.readString();
        headsmall = in.readString();
        sex = in.readString();
        personalized = in.readString();
        birthday = in.readString();
        love_status = in.readString();
        money = in.readString();
        province = in.readString();
        city = in.readString();
        job = in.readString();
        verify = in.readString();
        real_name = in.readString();
        idcard = in.readString();
        create_time = in.readString();
        store_name = in.readString();
        sig = in.readString();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeString(nickname);
        dest.writeString(livenum);
        dest.writeString(headsmall);
        dest.writeString(sex);
        dest.writeString(personalized);
        dest.writeString(birthday);
        dest.writeString(love_status);
        dest.writeString(money);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(job);
        dest.writeString(verify);
        dest.writeString(real_name);
        dest.writeString(idcard);
        dest.writeString(create_time);
        dest.writeString(store_name);
        dest.writeString(sig);
    }
}
