package com.teamdev.requestDto;

import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;

import java.util.Date;

public class MessageDto {
    private long id;
    private String text;
    private UserId userId;
    private ChatRoomId chatRoomId;
    private Date creationTime;

    public MessageDto(String text, UserId userId, ChatRoomId chatRoomId, Date creationTime) {
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

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public ChatRoomId getChatRoomId() {
        return chatRoomId;
    }

    public long getId() {
        return id;
    }
}
