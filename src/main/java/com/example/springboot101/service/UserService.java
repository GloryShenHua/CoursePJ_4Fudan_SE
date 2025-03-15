package com.example.springboot101.service;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    void register(String username, String password, String captcha, HttpServletRequest request); // 注册方法

    String login(String username, String password); // 登录方法，返回生成的 JWT Token
}