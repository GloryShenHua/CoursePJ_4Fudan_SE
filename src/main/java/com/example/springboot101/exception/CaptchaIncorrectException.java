package com.example.springboot101.exception;

public class CaptchaIncorrectException extends RuntimeException {

    public CaptchaIncorrectException(String message) {
        super(message);
    }

    public CaptchaIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
