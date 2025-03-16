package com.example.springboot101.exception;

public class PasswordIncorrectException extends RuntimeException {

    public PasswordIncorrectException(String message) {
        super(message);
    }

    public PasswordIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }
}
