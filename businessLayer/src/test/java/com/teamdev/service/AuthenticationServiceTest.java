package com.teamdev.service;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;
import com.teamdev.requestDto.wrappers.UserId;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthenticationServiceTest extends InitialData {

    private String name = "vasya1";
    private String email = "vasya1@gemail.com";
    private String password = "qwerty";

    @Test
    public void testSignUp() {
        UserDto userDto = new UserDto(name, email, password);

        try {
            UserId userId = authenticationService.signUp(name, email, password);
            assertNotEquals("User wasn't registered", 0 , userId);
        } catch (RegistrationException e) {
            fail("User has already existed in db");
        }

    }

    @Test
    public void testLogIn() {
        try {
            authenticationService.signUp(name, email, password);
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

        try {
            chatRoomService.createChatRoom(tokenDto.getAccessToken(), userId, "newRoom");
        } catch (ValidationException e) {
            assertEquals("Exception message is incorrect", "Validation data is invalid", e.getMessage());
        }
    }

    @Test
    public void testCheckToken() {
        UserId userId = signUp(name, email, password);
        AuthenticationTokenDto tokenDto = logIn(name, password);

        try {
            authenticationService.checkToken(tokenDto.getAccessToken(), userId);
        } catch (ValidationException e) {
            fail("Token is invalid");
        }
    }
}