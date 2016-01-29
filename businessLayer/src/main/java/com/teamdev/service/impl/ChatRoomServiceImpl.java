package com.teamdev.service.impl;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.MessageDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.jpa.model.ChatRoom;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.ChatRoomRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
        Set<ChatRoomDto> allChatRooms = new TreeSet<>();
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

        User user = userRepository.findOne(userId.getUserId());
        if (user == null) {
            throw new NullPointerException("User with current id wasn't found");
        }

        user.getChatRooms().add(currentChatRoom);
        currentChatRoom.getUsers().add(user);
        userRepository.save(user);
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

        Set<UserDto> usersDto = new TreeSet<>();
        Set<User> users = currentChatRoom.getUsers();
        for (User user: users) {
            usersDto.add(new UserDto(user.getId(), user.getName(), user.getMail(), null));
        }

        return ImmutableSet.copyOf(usersDto);
    }

    @Override
    public ChatRoomDto findChatRoom(String accessToken, UserId userId, ChatRoomId chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId.getChatRoomId());
        ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom.getId(), chatRoom.getRoomName());

        return chatRoomDto;
    }
}
