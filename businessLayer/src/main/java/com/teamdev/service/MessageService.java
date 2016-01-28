package com.teamdev.service;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.MessageDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.MessageId;
import com.teamdev.requestDto.wrappers.UserId;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
public interface MessageService {

    MessageId postMessage(String accessToken, UserId authorId, ChatRoomId chatRoomId, String text, Date creationTime) throws RuntimeException;

    ImmutableSet<MessageDto> findAllMessagesInChat(String accessToken, UserId userId, ChatRoomId chatRoomId);

    MessageId postPrivateMessage(String accessToken, UserId authorId, UserId recipientId, ChatRoomId chatRoomId, String text, Date creationTime);
}
