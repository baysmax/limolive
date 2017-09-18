package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/9/12.
 */

public class ChipBeatBean {//"table_number":"1","bet_money_sum":"20"
    private String table_number;//桌号

    public String getTable_number() {
        return table_number;
    }

    public void setTable_number(String table_number) {
        this.table_number = table_number;
    }

    public int getBet_money_sum() {
        return bet_money_sum;
    }

    public void setBet_money_sum(int bet_money_sum) {
        this.bet_money_sum = bet_money_sum;
    }

    private int bet_money_sum;//总数

    @Override
    public String toString() {
        return "ChipBeatBean{" +
                "table_number='" + table_number + '\'' +
                ", bet_money_sum='" + bet_money_sum + '\'' +
                '}';
    }
}
