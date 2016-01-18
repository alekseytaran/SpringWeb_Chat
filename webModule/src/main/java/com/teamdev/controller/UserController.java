package com.teamdev.controller;

import com.google.common.collect.ImmutableSet;
import com.teamdev.dto.ChatRoomDto;
import com.teamdev.dto.UserDto;
import com.teamdev.dto.wrappers.UserId;
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
    public UserDto getUserData(@RequestParam String token, @PathVariable String userid) {
        return userService.getUserData(token, new UserId(Integer.parseInt(userid)));
    }

    @RequestMapping(value = "/userchats/{userid}", params = {"token"}, method = RequestMethod.GET)
    @ResponseBody
    public ImmutableSet<ChatRoomDto> getUserChats(@RequestParam String token, @PathVariable String userid) {
        return userService.getUserChats(token, new UserId(Integer.parseInt(userid)));
    }
}
