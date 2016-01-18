package com.teamdev.dto;


public class AuthenticationTokenDto {

    private String accessToken;
    private Long userId;

    public AuthenticationTokenDto(String accessToken, Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
