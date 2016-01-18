package com.teamdev;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.dto.wrappers.UserId;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;
import com.teamdev.jpa.repository.AuthenticationRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
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

    private ApplicationContext context;

    private String name = "vasya";
    private String mail = "vasya@gmail.com";
    private String password = "qwerty";

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(AuthenticationServiceTest.class);
    }

    @Test
    public void testSignUp() {
        UserDto userDto = new UserDto(name, mail, password);
        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);

        try {
            authenticationService.signUp(userDto);
        } catch (RegistrationException e) {
            fail("User has already existed in db");
        }

        assertNotNull("User wasn't registered", context.getBean(UserRepository.class).findByPasswordAndName(password, name));
    }

    @Test
    public void testLogIn() {
        UserDto userDto = new UserDto(name, mail, password);
        AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
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
        AuthenticationTokenDto tokenDto = InitialData.logInInSystem(context, name, mail, password);
        assertNotNull("User doesn't exist in db", context.getBean(AuthenticationRepository.class).findByToken(tokenDto.getAccessToken()));
        context.getBean(AuthenticationService.class).logOut(tokenDto.getAccessToken(), new UserId(tokenDto.getUserId()));

        assertNull("User wasn't logged out", context.getBean(AuthenticationRepository.class).findByToken(tokenDto.getAccessToken()));
    }

}