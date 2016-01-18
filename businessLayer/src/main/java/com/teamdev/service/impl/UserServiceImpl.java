package com.teamdev.service.impl;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.dto.wrappers.UserId;
import com.teamdev.jpa.model.ChatRoom;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserData(String accessToken, UserId userId) throws RuntimeException {
        User user = userRepository.findOne(userId.getUserId());
        return new UserDto(user.getId(), user.getName(), user.getMail(), user.getPassword());
    }

    @Override
    public ImmutableSet<ChatRoomDto> getUserChats(String accessToken, UserId userId) {
        User user = userRepository.findOne(userId.getUserId());

        Set<ChatRoomDto> chatRoomsDto = new HashSet<>();
        for (ChatRoom chatRoom: user.getChatRooms()) {
            chatRoomsDto.add(new ChatRoomDto(chatRoom.getId(), chatRoom.getRoomName()));
        }

        return ImmutableSet.copyOf(chatRoomsDto);
    }
}
