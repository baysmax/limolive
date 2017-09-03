package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/22.
 */

public class BtnBean {
    private String id;
    private String name;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    private String type_id;

    public BtnBean() {
    }

    public BtnBean(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BtnBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type_id='" + type_id + '\'' +
                '}';
    }
}
