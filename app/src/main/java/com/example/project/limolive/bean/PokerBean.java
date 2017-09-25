package com.example.project.limolive.bean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AAA on 2017/9/13.
 */

public class PokerBean {
    /**
     *  {
     "sieve_id": "1125",
     "point": "1,5,3",
     "room_id": "2",
     "table_number": "0",
     "winorlose": ""
     }
     {
     "sieve_id": "117",
     "point": "13,4,10; 11,4,10; 11,2,10; 5,1,5; 3,1,3",
     "room_id": "4",
     "table_number": "0",
     "winorlose": "8",
     "point_sum": "8"
     },
     */
    private String sieve_id;
    private String point;
    private String room_id;
    private String[] chip_ch;
    private String table_number;
    private String winorlose;
    private Map<Integer,String[]> pokerMap=new HashMap<>();

    public String getPoint_sum() {
        return point_sum;
    }

    public void setPoint_sum(String point_sum) {
        this.point_sum = point_sum;
    }

    private String point_sum;

    public String getSieve_id() {
        return sieve_id;
    }

    public Map<Integer,String[]> getChip_ch() {
        if (point.contains(";")){
           chip_ch=point.split(";");
            for (int i = 0; i < chip_ch.length; i++) {
                String[] split = chip_ch[i].split(",");
                pokerMap.put(i,split);
            }
        }
        return pokerMap;
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
