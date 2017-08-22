package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/22.
 */

public class BtnBean {
    private String id;
    private String name;

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
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
