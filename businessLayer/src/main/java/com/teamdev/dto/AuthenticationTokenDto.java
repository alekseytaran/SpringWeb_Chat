package com.teamdev.dto;


public class AuthenticationTokenDto {

    private String accessToken;
    private long userId;

    public AuthenticationTokenDto(String accessToken, long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
