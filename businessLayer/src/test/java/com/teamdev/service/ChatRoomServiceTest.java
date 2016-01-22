package com.teamdev.service;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChatRoomServiceTest extends InitialData {

    private String name = "vasya";
    private String email = "vasya@gemail.com";
    private String password = "qwerty";
    private String roomName = "room";
    private String roomName1 = "room1";

    @Test
    public void testCreateChatRoom() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);

        assertEquals("ChatRoom wasn't create", 0, chatRoomService.findAllChatRooms(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId())).size());

        chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);

        assertEquals("ChatRoom wasn't create", 1, chatRoomService.findAllChatRooms(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId())).size());
    }

    @Test
    public void testGetAllChatRooms() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);

        chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName);
        chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), roomName1);

        assertEquals("ChatRoom wasn't create", 2, chatRoomService.findAllChatRooms(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId())).size());
    }

    @Test
    public void testJoinUserInChat() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);

        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), name);
        chatRoomService.joinUserToChat(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), chatRoomId);

        ImmutableSet<ChatRoomDto> chatRooms = userService.getUserChats(tokenDto.getAccessToken(), userId);
        ImmutableSet<UserDto> usersInChat = chatRoomService.getUsersDataInChat(tokenDto.getAccessToken(), userId, chatRoomId);

        assertEquals("User doesn't have room", 1, chatRooms.size());
        assertEquals("Room doesn't have user", 1, usersInChat.size());
    }

    @Test
    public void testGetUsersDataFromChat() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);

        ChatRoomId chatRoomId = chatRoomService.createChatRoom(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), name);
        chatRoomService.getUsersDataInChat(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()), chatRoomId);

        UserDto userData = userService.getUserData(tokenDto.getAccessToken(), userId);

        assertEquals("Incorrect name", "vasya", userData.getName());
        assertEquals("Incorrect email", "vasya@gemail.com", userData.getMail());
    }
}
