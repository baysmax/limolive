package com.example.project.limolive.bean.live;

/**
 * 作者：hpg on 2016/12/21 16:11
 * 功能：
 */
public class AllcatgoryBean {
    private String id;
    private String type_name;
    private String type_img;

    public AllcatgoryBean() {
    }

    public AllcatgoryBean(String id, String type_name, String type_img) {
        this.id = id;
        this.type_name = type_name;
        this.type_img = type_img;
    }

    @Override
    public String toString() {
        return "AllcatgoryBean{" +
                "id='" + id + '\'' +
                ", type_name='" + type_name + '\'' +
                ", type_img='" + type_img + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_img() {
        return type_img;
    }

    public void setType_img(String type_img) {
        this.type_img = type_img;
    }
}
