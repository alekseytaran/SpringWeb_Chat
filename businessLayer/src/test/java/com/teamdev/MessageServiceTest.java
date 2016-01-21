package com.teamdev;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.MessageDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.MessageId;
import com.teamdev.requestDto.wrappers.UserId;
import org.junit.Test;

import java.util.Date;

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
        AuthenticationTokenDto tokenDto = logIn(name, email, password);

        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);
        MessageId messageId = messageService.postMessage(tokenDto.getAccessToken(),
                new MessageDto(text, new UserId(tokenDto.getUserId()), new ChatRoomId(chatRoomId.getChatRoomId()), new Date(System.currentTimeMillis())));

        assertNotEquals("Message wasn't sent", 0, messageId);
    }
}
