package com.teamdev.controller;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.dto.wrappers.ChatRoomId;
import com.teamdev.dto.wrappers.UserId;
import com.teamdev.service.ChatRoomService;
import com.teamdev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/createchat/{userid}", params = {"token"}, method = RequestMethod.POST)
    @ResponseBody
    public ChatRoomId createChat(@RequestParam String token, @RequestBody ChatRoomDto chatRoomDto, @PathVariable String userid) {
        return chatRoomService.createChatRoom(token, new UserId(Integer.parseInt(userid)), chatRoomDto.getRoomName());
    }

    @RequestMapping(value = "/allchats/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<ChatRoomDto> findAllChats(@RequestParam String token, @PathVariable String userid) {
        return chatRoomService.findAllChatRooms(token, new UserId(Integer.parseInt(userid)));
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/join/{chatid}/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public void joinUserToChat(@RequestParam String token, @PathVariable String userid, @PathVariable String chatid) {
        chatRoomService.joinUserToChat(token, new UserId(Integer.parseInt(userid)), new ChatRoomId(Integer.parseInt(chatid)));
    }

    @RequestMapping(value = "/users/{chatid}/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<UserDto> getUsersFromChat(@RequestParam String token, @PathVariable String userid, @PathVariable String chatid) {
        return chatRoomService.getUsersDataInChat(token, new UserId(Integer.parseInt(userid)), new ChatRoomId(Integer.parseInt(chatid)));
    }


}
