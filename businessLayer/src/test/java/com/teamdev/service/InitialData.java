package com.teamdev.service;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;
import com.teamdev.requestDto.wrappers.UserId;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = SpringContextForTest.class)
public class InitialData {

    @Autowired
    protected AuthenticationService authenticationService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected ChatRoomService chatRoomService;
    @Autowired
    protected MessageService messageService;

    public UserId signUp(String name, String email, String password) {
        UserId userId = null;
        try {
            userId = authenticationService.signUp(name, email, password);
        } catch (RegistrationException e) {
            fail("User's already existed in db");
        }

        return userId;
    }

    public AuthenticationTokenDto logIn(String name, String password) {
        AuthenticationTokenDto tokenDto = null;

        try {
            tokenDto = authenticationService.logIn(password, name);
        } catch (ValidationException e) {
            fail("Credentials are incorrect");
        }

        return tokenDto;
    }
}
