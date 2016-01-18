package com.teamdev.requestDto.wrappers;

public class MessageId {

    private long messageId;

    public MessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
