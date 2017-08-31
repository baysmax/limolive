package com.example.project.limolive.bean.live;

import com.example.project.limolive.bean.Page;
import com.example.project.limolive.tencentlive.model.AvMemberInfo;

import java.util.List;

/**
 * Created by AAA on 2017/8/18.
 */

public class LivesInfoBean {
    private List<AvMemberInfo> lives;
    private String pageCount;

    private Page page;

    public LivesInfoBean(List<AvMemberInfo> lives, String pageCount, Page page) {
        this.lives = lives;
        this.pageCount = pageCount;
        this.page = page;
    }

    public LivesInfoBean() {
    }

    public List<AvMemberInfo> getLives() {
        return lives;
    }

    public void setLives(List<AvMemberInfo> lives) {
        this.lives = lives;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "LivesInfoBean{" +
                "lives=" + lives +
                ", pageCount='" + pageCount + '\'' +
                ", page='" + page + '\'' +
                '}';
    }
}
