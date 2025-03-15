package com.example.springboot101.repository;

import com.example.springboot101.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 根据用户名查询
    User findByUsername(String username);

    // 判断用户名是否已存在
    boolean existsByUsername(String username);
}
