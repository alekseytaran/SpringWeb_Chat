package com.teamdev;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;
import com.teamdev.jpa.repository.AuthenticationRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.junit.Assert.*;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.teamdev.repository.impl", "com.teamdev.service.impl", "com.teamdev.aspect"})
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;


    private String name = "vasya";
    private String email = "vasya@gemail.com";
    private String password = "qwerty";

    @Test
    public void testSignUp() {
        UserDto userDto = new UserDto(name, email, password);

        try {
            authenticationService.signUp(userDto);
        } catch (RegistrationException e) {
            fail("User has already existed in db");
        }

        assertNotNull("User wasn't registered", userRepository.findByPasswordAndName(password, name));
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
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(name, email, password);
        assertNotNull("User doesn't exist in db", context.getBean(AuthenticationRepository.class).findByAccessToken(tokenDto.getAccessToken()));
        context.getBean(AuthenticationService.class).logOut(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()));

        assertNull("User wasn't logged out", context.getBean(AuthenticationRepository.class).findByAccessToken(tokenDto.getAccessToken()));
    }

}