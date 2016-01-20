package com.teamdev.service;

import com.teamdev.dto.MessageDto;
import com.teamdev.requestDto.wrappers.MessageId;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MessageService {

    MessageId postMessage(String accessToken, MessageDto message) throws RuntimeException;

}
