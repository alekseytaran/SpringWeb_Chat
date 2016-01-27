package com.teamdev.service.impl;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.MessageDto;
import com.teamdev.requestDto.MessageRequestDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.MessageId;
import com.teamdev.jpa.model.ChatRoom;
import com.teamdev.jpa.model.Message;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.ChatRoomRepository;
import com.teamdev.jpa.repository.MessageRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Override
    public MessageId postMessage(String accessToken, UserId userId, ChatRoomId chatRoomId, String text, Date creationTime) throws RuntimeException {

        User user = userRepository.findOne(userId.getUserId());
        ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId.getChatRoomId());
        Message message = new Message(text, creationTime, user, chatRoom);

        return new MessageId(messageRepository.save(message).getId());
    }

    @Override
    public ImmutableSet<MessageDto> findAllMessagesInChat(String accessToken, UserId userId, ChatRoomId chatRoomId) {
        Set<MessageDto> allMessagesFromChat = new TreeSet<>();
        for (Message message: messageRepository.findByChatRoomId(chatRoomId.getChatRoomId())) {
            allMessagesFromChat.add(new MessageDto(message.getText(), new UserId(message.getUser().getId()),
                    new ChatRoomId(message.getChatRoom().getId())));
        }

        return ImmutableSet.copyOf(allMessagesFromChat);
    }


}
