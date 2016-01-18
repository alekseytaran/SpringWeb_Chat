package com.teamdev.controller;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
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

    @RequestMapping(value = "/chat/{userid}", params = {"token"}, method = RequestMethod.POST)
    @ResponseBody
    public ChatRoomId createChat(@RequestParam String token, @RequestBody ChatRoomDto chatRoomDto, @PathVariable int userid) {
        return chatRoomService.createChatRoom(token, new UserId(userid), chatRoomDto.getRoomName());
    }

    @RequestMapping(value = "/chats/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<ChatRoomDto> findAllChats(@RequestParam String token, @PathVariable int userid) {
        return chatRoomService.findAllChatRooms(token, new UserId(userid));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/join/{chatid}/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public void joinUserToChat(@RequestParam String token, @PathVariable int userid, @PathVariable int chatid) {
        chatRoomService.joinUserToChat(token, new UserId(userid), new ChatRoomId(chatid));
    }

    @RequestMapping(value = "/users/{chatid}/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<UserDto> getUsersFromChat(@RequestParam String token, @PathVariable int userid, @PathVariable int chatid) {
        return chatRoomService.getUsersDataInChat(token, new UserId(userid), new ChatRoomId(chatid));
    }


}
