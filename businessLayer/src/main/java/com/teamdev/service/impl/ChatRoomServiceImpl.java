package com.teamdev.service.impl;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.dto.wrappers.ChatRoomId;
import com.teamdev.dto.wrappers.UserId;
import com.teamdev.jpa.model.ChatRoom;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.ChatRoomRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ChatRoomId createChatRoom(String accessToken, UserId userId, String name) {
        if (name == null) {
            throw new NullPointerException("chat name is null");
        }

        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(name));

        return new ChatRoomId(chatRoom.getId());
    }

    @Override
    public ImmutableSet<ChatRoomDto> findAllChatRooms(String accessToken, UserId userId) {
        Set<ChatRoomDto> allChatRooms = new HashSet<>();
        for (ChatRoom chatRoom: chatRoomRepository.findAll()) {
            allChatRooms.add(new ChatRoomDto(chatRoom.getId(), chatRoom.getRoomName()));
        }

        return ImmutableSet.copyOf(allChatRooms);
    }

    @Override
    public void joinUserToChat(String accessToken, UserId userId, ChatRoomId chatRoomId) {
        ChatRoom currentChatRoom = chatRoomRepository.findOne(chatRoomId.getChatRoomId());
        if (currentChatRoom == null) {
            throw new NullPointerException("Chat room with current id wasn't found");
        }

        User userById = userRepository.findOne(userId.getUserId());
        if (userById == null) {
            throw new NullPointerException("User with current id wasn't found");
        }

        currentChatRoom.getUsers().add(userById);
        userById.getChatRooms().add(currentChatRoom);
    }

    @Override
    public ImmutableSet<UserDto> getUsersDataInChat(String accessToken, UserId userId, ChatRoomId chatRoomId) {
        ChatRoom currentChatRoom = chatRoomRepository.findOne(chatRoomId.getChatRoomId());
        if (currentChatRoom == null) {
            throw new NullPointerException("Chat room with current id wasn't found");
        }

        User userById = userRepository.findOne(userId.getUserId());
        if (userById == null) {
            throw new NullPointerException("User with current id wasn't found");
        }

        Set<UserDto> usersDto = new HashSet<>();
        Set<User> users = currentChatRoom.getUsers();
        for (User user: users) {
            usersDto.add(new UserDto(user.getId(), user.getName(), user.getMail(), null));
        }

        return ImmutableSet.copyOf(usersDto);
    }
}
