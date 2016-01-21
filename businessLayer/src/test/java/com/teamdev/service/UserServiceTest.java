package com.teamdev.service;


import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.UserId;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends InitialData {

    private String name = "vasya";
    private String email = "vasya@gemail.com";
    private String password = "qwerty";

    @Test
    public void getUserData() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);
        UserDto userData = userService.getUserData(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()));

        assertEquals("User name is incorrect", name, userData.getName());
        assertEquals("Mail is incorrect", email, userData.getMail());
    }
}
