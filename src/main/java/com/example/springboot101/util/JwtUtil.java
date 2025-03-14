package com.example.springboot101.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // 1. 用 JJWT 提供的 Keys 工具动态生成一个 HS256 密钥
    //   注意：这会在 JVM 启动时就生成并保存在静态变量里
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 2. 生成 Token
    public static String generateToken(String username) {
        long now = System.currentTimeMillis();
        long expire = now + 2 * 60 * 60 * 1000; // 2小时

        // 注意 signWith(...) 改为 signWith(key)
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(expire))
                .signWith(key)
                .compact();
    }

    // 3. 解析 Token
    public static String parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

