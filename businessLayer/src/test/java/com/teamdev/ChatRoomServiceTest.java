package com.teamdev;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.wrappers.ChatRoomId;
import com.teamdev.dto.wrappers.UserId;
import com.teamdev.jpa.model.ChatRoom;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.ChatRoomRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.ChatRoomService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChatRoomServiceTest {
    private ApplicationContext context;

    private String name = "vasya";
    private String mail = "vasya@gmail.com";
    private String password = "qwerty";
    private String roomName = "room";
    private String roomName1 = "room1";

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(AuthenticationServiceTest.class);
    }

    @Test
    public void testCreateChatRoom() {
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(context, name, mail, password);

        ChatRoomService chatRoomService = context.getBean(ChatRoomService.class);

        assertEquals("ChatRoom wasn't create", 0, chatRoomService.findAllChatRooms(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId())).size());

        chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);

        assertEquals("ChatRoom wasn't create", 1, chatRoomService.findAllChatRooms(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId())).size());
    }

    @Test
    public void testGetAllChatRooms() {
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(context, name, mail, password);

        ChatRoomService chatRoomService = context.getBean(ChatRoomService.class);
        chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);
        chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName1);

        assertEquals("ChatRoom wasn't create", 2, chatRoomService.findAllChatRooms(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId())).size());
    }

    @Test
    public void testJoinUserInChat() {
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(context, name, mail, password);

        ChatRoomService chatRoomService = context.getBean(ChatRoomService.class);
        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), name);
        chatRoomService.joinUserToChat(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), chatRoomId);

        User user = context.getBean(UserRepository.class).findByPasswordAndName(password, name);
        ChatRoom chatRoom = context.getBean(ChatRoomRepository.class).findOne(chatRoomId.getChatRoomId());

        assertEquals("User doesn't have room", 1, user.getChatRooms().size());
        assertEquals("Room doesn't have user", 1, chatRoom.getUsers().size());
    }

    @Test
    public void testGetUsersDataFromChat() {
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(context, name, mail, password);;

        ChatRoomService chatRoomService = context.getBean(ChatRoomService.class);
        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), name);
        chatRoomService.getUsersDataInChat(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), chatRoomId);

        User user = context.getBean(UserRepository.class).findByPasswordAndName(password, name);

        assertEquals("Incorrect name", "vasya", user.getName());
        assertEquals("Incorrect mail", "vasya@gmail.com", user.getMail());
    }
}
