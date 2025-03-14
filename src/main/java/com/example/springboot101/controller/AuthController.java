package com.example.springboot101.controller;

import com.example.springboot101.entity.User;
import com.example.springboot101.service.UserService;
import com.example.springboot101.util.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Response register(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String captcha,
                             HttpSession session) {
        // 1. 验证码校验
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(captcha)) {
            return new Response(400, "验证码错误");
        }

        // 2. 调用 service 进行注册
        try {
            userService.register(username, password);
            return new Response(200, "注册成功");
        } catch (RuntimeException e) {
            return new Response(400, e.getMessage());
        }
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Response login(@RequestParam String username,
                          @RequestParam String password) {
        try {
            User user = userService.login(username, password);
            // 生成 JWT
            String token = JwtUtil.generateToken(user.getUsername());
            return new Response(200, "登录成功", token);
        } catch (RuntimeException e) {
            return new Response(400, e.getMessage());
        }
    }

    // 通用返回结构
    static class Response {
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

        // getter & setter
        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public String getToken() {
            return token;
        }
    }
}

