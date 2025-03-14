package com.example.springboot101.service.impl;

import com.example.springboot101.entity.User;
import com.example.springboot101.repository.UserRepository;
import com.example.springboot101.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void register(String username, String rawPassword) {
        // 1. 用户名校验（长度、字符、是否已存在等）
        if (!username.matches("^[a-zA-Z0-9_]{4,20}$")) {
            throw new RuntimeException("用户名不合法：只允许字母、数字、下划线，长度4~20");
        }
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 密码校验（长度>=6，必须包含字母和数字）
        if (rawPassword.length() < 6) {
            throw new RuntimeException("密码长度不足6位");
        }
        if (!rawPassword.matches(".*[0-9].*") || !rawPassword.matches(".*[A-Za-z].*")) {
            throw new RuntimeException("密码必须包含数字和字母");
        }

        // 3. 加密密码再存储
        String encodedPass = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPass);
        userRepository.save(user);
    }

    @Override
    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 验证密码
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return user;
    }
}

