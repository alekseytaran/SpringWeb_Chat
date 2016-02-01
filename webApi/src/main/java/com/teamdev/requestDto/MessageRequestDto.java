package com.teamdev.requestDto;

import java.util.Date;

public class MessageRequestDto {
    private Long id;
    private String text;
    private Long userId;
    private Long chatRoomId;
    private Date creationTime;

    public MessageRequestDto(String text, Long userId, Long chatRoomId, Date creationTime) {
        this.text = text;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.creationTime = creationTime;
    }

    public String getText() {
        return text;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public Long getId() {
        return id;
    }
}
