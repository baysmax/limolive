package com.example.project.limolive.api;

import org.json.JSONObject;


public class ApiResponse {

    protected String data = "";
    protected String message = "";
    protected int code = -1;

    public ApiResponse(String data, String message) {
        this.data = data;
        this.message = message;
        this.code = 0;
    }

    public ApiResponse(org.json.JSONArray resp) {
        this.data = resp.toString();
        this.message = resp.toString();
        this.code = 0;
    }

    public ApiResponse(JSONObject resp) {
        if (resp == null)
            return;

        if (resp.has("data"))
            this.data = resp.optString("data");
        if (resp.has("message"))
            this.message = resp.optString("message");
        if (resp.has("code"))
            this.code = resp.optInt("code");
    }

    public ApiResponse() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "data='" + data + '\'' +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}
