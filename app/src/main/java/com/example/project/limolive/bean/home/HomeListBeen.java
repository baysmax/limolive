package com.example.project.limolive.bean.home;
/**
 * Created by Administrator on 2016/12/14.
 */


/**
 * HomeListBeen
 * 作者：李志超 on 2016/12/14
 */

public class HomeListBeen {


    //点赞人数
    private String admireCount;
    //直播房间号码
    private String avRoomId;
    //聊天室ID
    private String chatRoomId;
    //直播房间封面图
    private String cover;
    //创建时间
    private String createTime;
    //直播时长
    private String timeSpan;
    //直播标题
    private String title;
    //观看人数
    private String watchCount;
    //距离
    private String juli;
    //主播信息
    private HostInformationBeen host;
    //位置信息
    private AdressInformationBeen lbs;
    //是否关注
    private String is_follow;

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public String getAdmireCount() {
        return admireCount;
    }

    public void setAdmireCount(String admireCount) {
        this.admireCount = admireCount;
    }

    public String getAvRoomId() {
        return avRoomId;
    }

    public void setAvRoomId(String avRoomId) {
        this.avRoomId = avRoomId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWatchCount() {
        return watchCount;
    }

    public void setWatchCount(String watchCount) {
        this.watchCount = watchCount;
    }

    public HostInformationBeen getHost() {
        return host;
    }

    public void setHost(HostInformationBeen host) {
        this.host = host;
    }

    public AdressInformationBeen getLbs() {
        return lbs;
    }

    public void setLbs(AdressInformationBeen lbs) {
        this.lbs = lbs;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    @Override
    public String toString() {
        return "HomeListBeen{" +
                "admireCount='" + admireCount + '\'' +
                ", avRoomId='" + avRoomId + '\'' +
                ", chatRoomId='" + chatRoomId + '\'' +
                ", cover='" + cover + '\'' +
                ", createTime='" + createTime + '\'' +
                ", timeSpan='" + timeSpan + '\'' +
                ", title='" + title + '\'' +
                ", watchCount='" + watchCount + '\'' +
                ", juli='" + juli + '\'' +
                ", host=" + host +
                ", lbs=" + lbs +
                ", is_follow='" + is_follow + '\'' +
                '}';
    }
}
