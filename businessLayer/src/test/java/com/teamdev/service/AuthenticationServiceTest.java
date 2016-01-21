package com.teamdev.service;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;
import com.teamdev.requestDto.wrappers.UserId;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticationServiceTest extends InitialData {

    private String name = "vasya";
    private String email = "vasya@gemail.com";
    private String password = "qwerty";

    @Test
    public void testSignUp() {
        UserDto userDto = new UserDto(name, email, password);

        try {
            UserId userId = authenticationService.signUp(userDto);
            assertNotEquals("User wasn't registered", 0 , userId);
        } catch (RegistrationException e) {
            fail("User has already existed in db");
        }

    }

    @Test
    public void testLogIn() {
        UserDto userDto = new UserDto(name, email, password);
        try {
            authenticationService.signUp(userDto);
        } catch (RegistrationException e) {
            fail("User's already existed in db");
        }

        try {
            assertNotNull("User wasn't logged in", authenticationService.logIn(password, name));
        } catch (ValidationException e) {
            fail("Credentials are incorrect");
        }
    }

    @Test
    public void testLogOut() throws ValidationException {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);
        authenticationService.logOut(tokenDto.getAccessToken(), userId);

        assertNull("User wasn't logged out", authenticationService.logIn(password, name));
    }

}