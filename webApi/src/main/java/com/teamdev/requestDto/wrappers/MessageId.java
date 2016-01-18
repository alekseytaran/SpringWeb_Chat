package com.teamdev.requestDto.wrappers;

public class MessageId {

    private Long messageId;

    public MessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }
}
