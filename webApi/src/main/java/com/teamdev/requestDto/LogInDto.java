package com.teamdev.requestDto;

public class LogInDto {

    public final String password;
    public final String name;

    public LogInDto(String password, String name) {
        this.password = password;
        this.name = name;
    }
}
