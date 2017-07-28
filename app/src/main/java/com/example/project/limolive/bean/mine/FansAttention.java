package com.example.project.limolive.bean.mine;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * fans and attention
 * @author hwj on 2016/12/19.
 */

public class FansAttention implements Parcelable{

    public static final int SELECTED=1;
    public static final int UNSELECTED=0;

    private String uid;
    private String headsmall;
    private String nickname;
    private String livenum;
    private String sex;
    private int isSelect;

    public FansAttention(){}

    protected FansAttention(Parcel in) {
        uid = in.readString();
        headsmall = in.readString();
        nickname = in.readString();
        livenum = in.readString();
        sex = in.readString();
        isSelect = in.readInt();
    }

    public static final Creator<FansAttention> CREATOR = new Creator<FansAttention>() {
        @Override
        public FansAttention createFromParcel(Parcel in) {
            return new FansAttention(in);
        }

        @Override
        public FansAttention[] newArray(int size) {
            return new FansAttention[size];
        }
    };

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHeadsmall() {
        return headsmall;
    }

    public void setHeadsmall(String headsmall) {
        this.headsmall = headsmall;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(headsmall);
        dest.writeString(nickname);
        dest.writeString(livenum);
        dest.writeString(sex);
        dest.writeInt(isSelect);
    }
}
