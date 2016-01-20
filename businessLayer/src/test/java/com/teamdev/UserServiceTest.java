package com.teamdev;


import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertEquals;

public class UserServiceTest {

    private String name = "vasya";
    private String email = "vasya@gemail.com";
    private String password = "qwerty";

    @Test
    public void getUserData() {
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(context, name, email, password);
        UserDto userData = context.getBean(UserService.class).getUserData(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()));

        assertEquals("User name is incorrect", name, userData.getName());
        assertEquals("Mail is incorrect", email, userData.getMail());
    }
}
