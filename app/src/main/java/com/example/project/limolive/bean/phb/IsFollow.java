package com.example.project.limolive.bean.phb;

/**
 * Created by AAA on 2017/8/17.
 */

public class IsFollow {
    @Override
    public String toString() {
        return "IsFollow{" +
                "is_follow='" + is_follow + '\'' +
                '}';
    }

    private String is_follow;

    public IsFollow() {
    }

    public IsFollow(String is_follow) {
        this.is_follow = is_follow;
    }

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }
}
