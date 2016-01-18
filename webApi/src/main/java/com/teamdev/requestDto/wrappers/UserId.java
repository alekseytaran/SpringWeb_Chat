package com.teamdev.requestDto.wrappers;

public class UserId {

    private long userId;

    public UserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
