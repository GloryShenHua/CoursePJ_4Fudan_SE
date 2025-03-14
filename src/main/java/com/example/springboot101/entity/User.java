package com.example.springboot101.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // 数据库中的表名
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 用户名只允许包含字母、数字、下划线，长度在4~20
    @Column(nullable = false, unique = true, length = 20)
    private String username;

    // 密码：加密后存储，长度给大一些
    @Column(nullable = false, length = 100)
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter & Setter ...
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // 存储数据库的是加密之后的值
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
