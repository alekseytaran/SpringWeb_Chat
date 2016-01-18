package com.teamdev.service.impl;

import com.teamdev.dto.MessageDto;
import com.teamdev.dto.wrappers.MessageId;
import com.teamdev.jpa.model.ChatRoom;
import com.teamdev.jpa.model.Message;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.ChatRoomRepository;
import com.teamdev.jpa.repository.MessageRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.AuthenticationService;
import com.teamdev.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Override
    public MessageId postMessage(String accessToken, MessageDto messageDto) throws RuntimeException {

        User user = userRepository.findOne(messageDto.getUserId().getUserId());
        ChatRoom chatRoom = chatRoomRepository.findOne(messageDto.getChatRoomId().getChatRoomId());
        Message message = new Message(messageDto.getText(), messageDto.getCreationTime(), user, chatRoom);

        chatRoom.getMessages().add(message);
        user.getMessages().add(message);

        return new MessageId(messageRepository.save(message).getId());
    }


}
