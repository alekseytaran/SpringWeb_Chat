package com.teamdev.requestDto;

import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;

import java.util.Date;

public class MessageRequestDto {
    private Long id;
    private String text;
    private Long userId;
    private Long chatRoomId;
    private Date creationTime;

    public MessageRequestDto(String text, Long userId, Long chatRoomId) {
        this.text = text;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.creationTime = new Date(System.currentTimeMillis());
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
