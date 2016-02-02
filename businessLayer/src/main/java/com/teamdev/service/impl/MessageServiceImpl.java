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
    public MessageId postMessage(String accessToken, UserId authorId, ChatRoomId chatRoomId, String text, Date creationTime) throws RuntimeException {
        User author = userRepository.findOne(authorId.getUserId());
        ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId.getChatRoomId());
        Message message = new Message(text, creationTime, author,  chatRoom);

        return new MessageId(messageRepository.save(message).getId());
    }

    @Override
    public ImmutableSet<MessageDto> findAllMessagesInChat(String accessToken, UserId userId, ChatRoomId chatRoomId) {
        Set<MessageDto> allMessagesFromChat = new TreeSet<>();
        User user = userRepository.findOne(userId.getUserId());
        for (Message message: messageRepository.findByChatRoomId(chatRoomId.getChatRoomId())) {
            if (message.getRecipient() == null) {
                allMessagesFromChat.add(new MessageDto(message.getId(), message.getText(), message.getUser().getId(), message.getUser().getName(),
                        null, message.getChatRoom().getId(), message.getCreationTime()));
            }

            if (message.getRecipient() != null && (message.getRecipient() == user || message.getUser() == user)) {
                allMessagesFromChat.add(new MessageDto(message.getId(), message.getText(), message.getUser().getId(), message.getUser().getName(),
                        message.getRecipient().getName(), message.getChatRoom().getId(), message.getCreationTime()));
            }
        }

        return ImmutableSet.copyOf(allMessagesFromChat);
    }

    @Override
    public MessageId postPrivateMessage(String accessToken, UserId authorId, UserId recipientId, ChatRoomId chatRoomId, String text, Date creationTime) {
        User author = userRepository.findOne(authorId.getUserId());
        User recipient = userRepository.findOne(recipientId.getUserId());
        ChatRoom chatRoom = chatRoomRepository.findOne(chatRoomId.getChatRoomId());
        Message message = new Message(text, creationTime, author, recipient, chatRoom);

        return new MessageId(messageRepository.save(message).getId());
    }
}
