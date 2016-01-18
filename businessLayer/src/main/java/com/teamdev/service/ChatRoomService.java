package com.teamdev.service;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.dto.wrappers.ChatRoomId;
import com.teamdev.dto.wrappers.UserId;

public interface ChatRoomService {

    ChatRoomId createChatRoom(String accessToken, UserId userId, String name);

    ImmutableSet<ChatRoomDto> findAllChatRooms(String accessToken, UserId userId);

    void joinUserToChat(String accessToken, UserId userId, ChatRoomId chatRoomId);

    ImmutableSet<UserDto> getUsersDataInChat(String accessToken, UserId userId, ChatRoomId chatRoomId);
}
