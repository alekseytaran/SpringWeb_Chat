package com.teamdev.controller;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.MessageDto;
import com.teamdev.requestDto.MessageRequestDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.MessageId;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message/{userid}", params = {"token"}, method = RequestMethod.POST)
    @ResponseBody
    public MessageId postMessage(@RequestParam String token, @RequestBody MessageRequestDto messageRequestDto, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        ChatRoomId chatRoomId = new ChatRoomId(messageRequestDto.getChatRoomId());
        String text = messageRequestDto.getText();
        return messageService.postMessage(token, userId, chatRoomId, text, new Date(System.currentTimeMillis()));
    }

    @RequestMapping(value = "/messages/{userid}/{chatid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<MessageDto> getMessagesFromChat(@RequestParam String token, @PathVariable Long userid, @PathVariable Long chatid) {
        UserId userId = new UserId(userid);
        ChatRoomId chatRoomId = new ChatRoomId(chatid);
        return messageService.findAllMessagesInChat(token, userId, chatRoomId);
    }


}
