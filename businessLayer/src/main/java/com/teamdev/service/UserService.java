package com.teamdev.service;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.UserId;

public interface UserService {

    UserDto getUserData(String accessToken, UserId userId) throws RuntimeException;

    ImmutableSet<ChatRoomDto> getUserChats(String accessToken, UserId userId);

}
