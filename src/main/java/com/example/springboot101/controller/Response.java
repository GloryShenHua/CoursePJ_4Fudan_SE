package com.example.springboot101.controller;

public class Response {

    private int code;
    private String msg;
    private String token;  // 用于登录成功时返回

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(int code, String msg, String token) {
        this.code = code;
        this.msg = msg;
        this.token = token;
    }

    // Getter & Setter...
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
