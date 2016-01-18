package com.teamdev.service;

import com.teamdev.dto.MessageDto;
import com.teamdev.dto.wrappers.MessageId;

public interface MessageService {

    MessageId postMessage(String accessToken, MessageDto message) throws RuntimeException;

}
