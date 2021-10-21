package com.ubitricity.codingchallenge.controller.advice.model;

import lombok.Data;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;

@Data
public class ErrorInfo {

    public final Integer errorCode;
    public final String date;
    public final String message;

    public ErrorInfo(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.date = now().format(ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }
}
