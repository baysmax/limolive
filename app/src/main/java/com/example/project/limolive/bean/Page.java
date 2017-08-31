package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/30.
 */

public class Page {
    private String pageCount;
    private String page;
    private String group_jqr;

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getGroup_jqr() {
        return group_jqr;
    }

    public void setGroup_jqr(String group_jqr) {
        this.group_jqr = group_jqr;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageCount='" + pageCount + '\'' +
                ", page='" + page + '\'' +
                ", group_jqr='" + group_jqr + '\'' +
                '}';
    }
}
