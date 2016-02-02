package com.teamdev.service;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.exception.RegistrationException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthenticationService {

    UserId signUp(String name, String email, String password) throws RegistrationException;

    AuthenticationTokenDto logIn(String password, String name);

    void logOut(String accessToken, UserId userId);

    void deleteUser(String accessToken, UserId userId);

    void checkToken(String accessToken, UserId userId);

}
