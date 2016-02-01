package com.teamdev.dto;

import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.MessageId;
import com.teamdev.requestDto.wrappers.UserId;

import java.util.Date;

public class MessageDto implements Comparable<MessageDto> {
    private Long id;
    private String text;
    private Long userId;
    private String userName;
    private String recipientName;
    private Long chatRoomId;
    private Date creationTime;

    public MessageDto(Long messageId, String text, Long userId, String userName, String recipientName, Long chatRoomId, Date creationTime) {
        this.id = messageId;
        this.text = text;
        this.userId = userId;
        this.userName = userName;
        this.recipientName = recipientName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    @Override
    public int compareTo(MessageDto anotherMessageDto) {
        return this.getCreationTime().getTime() > anotherMessageDto.getCreationTime().getTime() ? 1 : -1;
    }
}
