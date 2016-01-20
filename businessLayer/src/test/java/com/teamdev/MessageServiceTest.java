package com.teamdev;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.MessageDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.jpa.model.ChatRoom;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.ChatRoomRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.ChatRoomService;
import com.teamdev.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class MessageServiceTest {

    private String name = "vasya";
    private String email = "vasya@gemail.com";
    private String password = "qwerty";
    private String roomName = "room";
    private String text = "message text";

    @Test
    public void testPostMessage() {
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(context, name, email, password);

        ChatRoomId chatRoomId = context.getBean(ChatRoomService.class).createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);
        context.getBean(MessageService.class).postMessage(tokenDto.getAccessToken(),
                new MessageDto(text, new UserId(tokenDto.getUserId()), new ChatRoomId(chatRoomId.getChatRoomId()), new Date(System.currentTimeMillis())));

        User user = context.getBean(UserRepository.class).findByPasswordAndName(password, name);
        ChatRoom chatRoom = context.getBean(ChatRoomRepository.class).findOne(chatRoomId.getChatRoomId());

        assertEquals("User doesn't have message", 1, user.getMessages().size());
        assertEquals("Room doesn't have message", 1, chatRoom.getMessages().size());
    }
}
