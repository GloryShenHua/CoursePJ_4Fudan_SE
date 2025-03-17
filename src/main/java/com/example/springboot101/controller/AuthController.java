package com.example.springboot101.controller;

import com.example.springboot101.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // 用户注册接口
    @PostMapping("/register")
    public Response register(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String captcha,
                             HttpServletRequest request) {
        userService.register(username, password, captcha, request); // 调用注册方法
        return new Response(200, "Registration successful");
    }

    // 用户登录接口
    @PostMapping("/login")
    public Response login(@RequestParam String username,
                          @RequestParam String password) {
        // 调用登录方法，并返回生成的 JWT Token
        String token = userService.login(username, password);
        return new Response(200, "Login successful", token); // 返回 token
    }
}
