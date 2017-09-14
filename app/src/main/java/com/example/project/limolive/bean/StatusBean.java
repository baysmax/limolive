package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/9/12.
 */

public class StatusBean {
    //{"state_id":"3","bet_count_down":"0","rest_count_down":"0","game_state":"0","room_id":"2"}'
    private String state_id;
    private String bet_count_down;
    private String rest_count_down;
    private String game_state;
    private String room_id;

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getBet_count_down() {
        return bet_count_down;
    }

    public void setBet_count_down(String bet_count_down) {
        this.bet_count_down = bet_count_down;
    }

    public String getRest_count_down() {
        return rest_count_down;
    }

    public void setRest_count_down(String rest_count_down) {
        this.rest_count_down = rest_count_down;
    }

    public String getGame_state() {
        return game_state;
    }

    public void setGame_state(String game_state) {
        this.game_state = game_state;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    @Override
    public String toString() {
        return "StatusBean{" +
                "state_id='" + state_id + '\'' +
                ", bet_count_down='" + bet_count_down + '\'' +
                ", rest_count_down='" + rest_count_down + '\'' +
                ", game_state='" + game_state + '\'' +
                ", room_id='" + room_id + '\'' +
                '}';
    }
}
