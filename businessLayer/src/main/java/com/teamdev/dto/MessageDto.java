package com.teamdev.dto;

import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;

import java.util.Date;

public class MessageDto implements Comparable<MessageDto> {
    private Long id;
    private String text;
    private UserId userId;
    private ChatRoomId chatRoomId;
    private Date creationTime;

    public MessageDto(String text, UserId userId, ChatRoomId chatRoomId) {
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

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public ChatRoomId getChatRoomId() {
        return chatRoomId;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int compareTo(MessageDto anotherMessageDto) {
        return this.getCreationTime().getTime() > anotherMessageDto.getCreationTime().getTime() ? 1 : -1;
    }
}
