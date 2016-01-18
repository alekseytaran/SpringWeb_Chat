package com.teamdev.requestDto.wrappers;

public class UserId {

    private Long userId;

    public UserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
