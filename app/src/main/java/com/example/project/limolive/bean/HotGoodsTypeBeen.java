package com.example.project.limolive.bean;/**
 * Created by Administrator on 2017/1/11.
 */

/**
 * AllcatgoryBeen
 * 作者：李志超 on 2017/1/11 19:14
 */

public class HotGoodsTypeBeen {
    private String id;
    private String name;

    public HotGoodsTypeBeen(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public HotGoodsTypeBeen() {
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
        return "HotGoodsTypeBeen{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
