package com.example.project.limolive.bean;

import java.io.Serializable;

/**
 * Created by AAA on 2017/8/8.
 */

public class SystemMsgBean implements Serializable {
    private String id;
    private String notice_content;
    private String notice_name;

    public SystemMsgBean() {
    }
    public SystemMsgBean(String id, String notice_content, String notice_name) {
        this.id = id;
        this.notice_content = notice_content;
        this.notice_name = notice_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public String getNotice_name() {
        return notice_name;
    }

    public void setNotice_name(String notice_name) {
        this.notice_name = notice_name;
    }

    @Override
    public String toString() {
        return "SystemMsgBean{" +
                "id='" + id + '\'' +
                ", notice_content='" + notice_content + '\'' +
                ", notice_name='" + notice_name + '\'' +
                '}';
    }
}
