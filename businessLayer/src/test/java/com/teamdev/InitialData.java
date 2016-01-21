package com.teamdev;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.AuthenticationService;
import com.teamdev.service.ChatRoomService;
import com.teamdev.service.MessageService;
import com.teamdev.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.teamdev.SpringContextForTest.class, loader = AnnotationConfigContextLoader.class)
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
        UserDto userDto = new UserDto(name, email, password);
        UserId userId = null;
        try {
            userId = authenticationService.signUp(userDto);
        } catch (RegistrationException e) {
            fail("User's already existed in db");
        }

        return userId;
    }

    public AuthenticationTokenDto logIn(String name, String email, String password) {
        AuthenticationTokenDto tokenDto = null;

        try {
            tokenDto = authenticationService.logIn(password, name);
        } catch (ValidationException e) {
            fail("Credentials are incorrect");
        }

        return tokenDto;
    }
//
//    public static void createSampleData() {
//                final String password1 = "qwerty";
//        final String password2 = "123456";
//        final String name1 = "vasya";
//        final String name2 = "petya";
//        final String email1 = "vasya@gemail.com";
//        final String email2 = "petya@gemail.com";
//        final String roomName1 = "room1";
//        final String roomName2 = "room2";
//
//        ChatRoomService chatRoomService = context.getBean(ChatRoomService.class);
//        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
//
//        UserDto firstUser = new UserDto(name1, email1, password1);
//        UserDto secondUser = new UserDto(name2, email2, password2);
//        UserId firstUserId = authenticationService.signUp(firstUser);
//        UserId secondUserId = authenticationService.signUp(secondUser);
//        try {
//            authenticationService.logIn(password1, name1);
//            authenticationService.logIn(password2, name2);
//        } catch (ValidationException e) {
//            fail("Log in credentials are incorrect");
//        }
//
//
//        ChatRoomId chatRoomId1 = chatRoomService.createChatRoom("qwertyvasya", new UserId(1L), roomName1);
//        ChatRoomId chatRoomId2 = chatRoomService.createChatRoom("123456petya", new UserId(2L), roomName2);
//
//        chatRoomService.joinUserToChat("qwertyvasya", firstUserId, chatRoomId1);
//        chatRoomService.joinUserToChat("qwertyvasya", firstUserId, chatRoomId2);
//        chatRoomService.joinUserToChat("123456petya", secondUserId, chatRoomId2);
//    }
}
