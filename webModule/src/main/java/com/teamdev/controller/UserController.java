package com.teamdev.controller;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public UserDto getUserData(@RequestParam String token, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        return userService.getUserData(token, userId);
    }

    @RequestMapping(value = "/user/chats/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<ChatRoomDto> getUserChats(@RequestParam String token, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        return userService.getUserChats(token, userId);
    }
}
