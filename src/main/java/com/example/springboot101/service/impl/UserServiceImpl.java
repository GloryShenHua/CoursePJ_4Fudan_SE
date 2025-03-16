package com.example.springboot101.service.impl;

import com.example.springboot101.entity.User;
import com.example.springboot101.exception.InvalidUsernameException;
import com.example.springboot101.exception.InvalidPasswordException;
import com.example.springboot101.exception.UsernameAlreadyExistsException;
import com.example.springboot101.exception.UserNotFoundException;
import com.example.springboot101.exception.CaptchaIncorrectException;
import com.example.springboot101.repository.UserRepository;
import com.example.springboot101.service.UserService;
import com.example.springboot101.util.JwtUtil;  // 导入 JwtUtil 类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    // 用户注册逻辑，确保签名与接口一致
    @Override
    public void register(String username, String password, String captcha, HttpServletRequest request) {
        // 校验验证码
        if (!isCaptchaValid(captcha, request)) {
            throw new CaptchaIncorrectException("Captcha is incorrect");
        }

        // 校验用户名是否合法
        if (!isUsernameValid(username)) {
            throw new InvalidUsernameException("Invalid username: Only letters, digits, and underscores are allowed, length must be between 4 and 20.");
        }

        // 校验密码是否合法
        if (!isPasswordValid(password)) {
            throw new InvalidPasswordException("Invalid password: Password must contain both letters and digits, and be at least 6 characters long.");
        }

        // 检查用户名是否已存在
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists: " + username);
        }

        // 注册用户
        User user = new User(username, password);
        userRepository.save(user);
    }

    // 用户登录逻辑
    @Override
    public String login(String username, String password) {
        // 检查用户是否存在
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found: " + username);
        }

        // 校验密码
        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException("Incorrect password for user: " + username);
        }

        // 生成 JWT Token 使用 JwtUtil 生成 Token
        return JwtUtil.generateToken(username); // 使用 JwtUtil 来生成 Token
    }

    // 校验用户名是否合法
    private boolean isUsernameValid(String username) {
        return username.length() >= 4 && username.length() <= 20 && Pattern.matches("^[a-zA-Z0-9_]+$", username);
    }

    // 校验密码是否合法
    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && Pattern.matches(".*[0-9].*", password) && Pattern.matches(".*[a-zA-Z].*", password);
    }

    // 校验验证码是否合法
    private boolean isCaptchaValid(String captcha, HttpServletRequest request) {
        // 从 Session 中获取生成的验证码
        String sessionCaptcha = (String) request.getSession().getAttribute("captcha");
        return captcha != null && captcha.equals(sessionCaptcha);
    }
}