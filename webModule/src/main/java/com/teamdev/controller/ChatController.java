package com.teamdev.controller;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.ChatRoomRequestDto;
import com.teamdev.requestDto.wrappers.ChatRoomId;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;

    @RequestMapping(value = "/chat/{chatid}/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ChatRoomDto findChat(@RequestParam String token, @PathVariable Long userid, @PathVariable Long chatid) {
        UserId userId = new UserId(userid);
        ChatRoomId chatRoomId = new ChatRoomId(chatid);
        return chatRoomService.findChatRoom(token, userId, chatRoomId);
    }

    @RequestMapping(value = "/chat/{userid}", params = {"token"}, method = RequestMethod.POST)
    @ResponseBody
    public ChatRoomId createChat(@RequestParam String token, @RequestBody ChatRoomRequestDto chatRoomDto, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        String roomName = chatRoomDto.getRoomName();
        return chatRoomService.createChatRoom(token, userId, roomName);
    }

    @RequestMapping(value = "/chats/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<ChatRoomDto> findAllChats(@RequestParam String token, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        return chatRoomService.findAllChatRooms(token, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/join/{chatid}/{userid}", params = {"token"}, method = RequestMethod.POST)
    @ResponseBody
    public void joinUserToChat(@RequestParam String token, @PathVariable Long userid, @PathVariable Long chatid) {
        UserId userId = new UserId(userid);
        ChatRoomId chatRoomId = new ChatRoomId(chatid);
        chatRoomService.joinUserToChat(token, userId, chatRoomId);
    }

    @RequestMapping(value = "/users/{chatid}/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<UserDto> findUsersFromChat(@RequestParam String token, @PathVariable Long userid, @PathVariable Long chatid) {
        UserId userId = new UserId(userid);
        ChatRoomId chatRoomId = new ChatRoomId(chatid);
        return chatRoomService.getUsersDataInChat(token, userId, chatRoomId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/leave/{chatid}/{userid}", params = {"token"}, method = RequestMethod.POST)
    @ResponseBody
    public void leaveChat(@RequestParam String token, @PathVariable Long userid, @PathVariable Long chatid) {
        UserId userId = new UserId(userid);
        ChatRoomId chatRoomId = new ChatRoomId(chatid);
        chatRoomService.leaveChat(token, userId, chatRoomId);
    }
}
