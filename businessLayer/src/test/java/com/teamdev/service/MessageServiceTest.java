package com.teamdev.service;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.MessageDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.MessageId;
import com.teamdev.requestDto.wrappers.UserId;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MessageServiceTest extends InitialData{

    private String name = "vasya";
    private String email = "vasya@gemail.com";
    private String password = "qwerty";
    private String roomName = "room";
    private String text = "message text";

    @Test
    public void testPostMessage() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);

        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);
        MessageId messageId = messageService.postMessage(tokenDto.getAccessToken(), userId, chatRoomId, text, new Date(System.currentTimeMillis()));

        assertNotEquals("Message wasn't sent", 0, messageId);
    }

    @Test
    public void testPrivateMessage() {
        UserId userId = signUp(name, email, password);
        UserId recipientId = signUp("petya", "petya@gmail.com", "qwerty");
        AuthenticationTokenDto tokenDto = logIn(name, password);

        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);
        MessageId messageId = messageService.postPrivateMessage(tokenDto.getAccessToken(), userId, recipientId, chatRoomId, text, new Date(System.currentTimeMillis()));
        ImmutableSet<MessageDto> allMessagesInChat = messageService.findAllMessagesInChat(tokenDto.getAccessToken(), userId, chatRoomId);

        String recipientName = allMessagesInChat.iterator().next().getRecipientName();
        assertNotEquals("Message doesn't have recipient", null, recipientName);
        assertNotEquals("Message wasn't sent", 0, messageId);
    }

    @Test
    public void testFindAllMessages() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);

        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);
        messageService.postMessage(tokenDto.getAccessToken(), userId, chatRoomId, text, new Date(System.currentTimeMillis()));

        assertEquals("Count of messages is incorrect", 1, messageService.findAllMessagesInChat(tokenDto.getAccessToken(), userId, chatRoomId).size());
    }
}
