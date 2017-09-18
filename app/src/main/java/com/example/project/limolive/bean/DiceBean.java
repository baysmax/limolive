package com.example.project.limolive.bean;

import java.util.Arrays;

/**
 * Created by AAA on 2017/9/13.
 */

public class DiceBean {
    /**
     *  {
     "sieve_id": "1125",
     "point": "1,5,3",
     "room_id": "2",
     "table_number": "0",
     "winorlose": ""
     }
     */
    private String sieve_id;
    private String point;
    private String room_id;
    private String[] chip_ch;
    private String table_number;
    private String winorlose;

    public String getSieve_id() {
        return sieve_id;
    }

    public String[] getChip_ch() {
        if (point.contains(",")){
           chip_ch=point.split(",");
        }
        return chip_ch;
    }


    public void setSieve_id(String sieve_id) {
        this.sieve_id = sieve_id;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_number) {
        this.table_number = table_number;
    }

    public String getWinorlose() {
        return winorlose;
    }

    public void setWinorlose(String winorlose) {
        this.winorlose = winorlose;
    }

    @Override
    public String toString() {
        return "DiceBean{" +
                "sieve_id='" + sieve_id + '\'' +
                ", point='" + point + '\'' +
                ", room_id='" + room_id + '\'' +
                ", chip_ch=" + Arrays.toString(chip_ch) +
                ", table_number='" + table_number + '\'' +
                ", winorlose='" + winorlose + '\'' +
                '}';
    }
}
