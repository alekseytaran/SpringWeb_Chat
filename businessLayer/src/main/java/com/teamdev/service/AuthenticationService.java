package com.teamdev.service;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.dto.wrappers.UserId;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;

import java.nio.file.AccessDeniedException;

public interface AuthenticationService {

    UserId signUp(UserDto user) throws RegistrationException;

    AuthenticationTokenDto logIn(String password, String name);

    void logOut(String accessToken, UserId userId);

    void deleteUser(String accessToken, UserId userId);

    void checkToken(String accessToken, UserId userId);

}
