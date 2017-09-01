package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/9/1.
 */

public class LvGrade {
    private String grade;

    private String img;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "LvGrade{" +
                "grade='" + grade + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
