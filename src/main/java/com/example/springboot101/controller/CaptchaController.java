package com.example.springboot101.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 生成验证码的示例控制器
 */
@RestController
public class CaptchaController {

    @Autowired
    private DefaultKaptcha kaptchaProducer;

    /**
     * 生成验证码图片
     */
    @GetMapping("/api/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 告诉浏览器，这是一张图片
        response.setContentType("image/jpeg");

        // 生成验证码文本
        String text = kaptchaProducer.createText();
        // 生成图片
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入 Session 或 Redis，供后面校验使用
        request.getSession().setAttribute("captcha", text);

        // 写入 response 输出流，让前端显示验证码图片
        ImageIO.write(image, "jpg", response.getOutputStream());
    }
}

