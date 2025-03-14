package com.example.springboot101.service;

import com.example.springboot101.entity.User;

public interface UserService {

    /**
     * 注册用户
     * @param username 用户名
     * @param rawPassword 原始密码
     */
    void register(String username, String rawPassword);

    /**
     * 登录校验
     * @param username 用户名
     * @param rawPassword 原始密码
     * @return 如果验证通过，返回对应User信息；否则抛出异常
     */
    User login(String username, String rawPassword);
}