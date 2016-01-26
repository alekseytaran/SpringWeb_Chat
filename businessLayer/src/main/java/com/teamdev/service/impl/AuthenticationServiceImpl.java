package com.teamdev.service.impl;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.exception.RegistrationException;
import com.teamdev.exception.ValidationException;
import com.teamdev.jpa.model.AuthenticationToken;
import com.teamdev.jpa.model.User;
import com.teamdev.jpa.repository.AuthenticationRepository;
import com.teamdev.jpa.repository.UserRepository;
import com.teamdev.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserId signUp(UserDto userDto) {
        if (userDto == null) {
            throw new NullPointerException("user is null");
        }
        if (!(userRepository.findByPasswordAndName(userDto.getPassword(), userDto.getName()) == null)) {
            throw new RegistrationException("user with this credentials has already existed in DB");
        }

        User userEntity = new User(userDto.getName(), userDto.getMail(), userDto.getPassword());
        User user = userRepository.save(userEntity);

        return new UserId(user.getId());
    }

    @Override
    public AuthenticationTokenDto logIn(String password, String name) {
        if (password == null || name == null) {
            throw new NullPointerException("login data is null");
        }

        String accessToken = password + name;

        User user = userRepository.findByPasswordAndName(password, name);
        if (user == null) {
            throw new ValidationException("User with current credentials not found");
        }

        int periodTokenLife = 1000 * 60 * 30;
        Date validTime = new Date(System.currentTimeMillis() + periodTokenLife);

        AuthenticationToken authenticationToken = new AuthenticationToken(accessToken, user, validTime);

        if (authenticationRepository.findByAccessToken(accessToken) != null) {
            throw new ValidationException("Access token has already existed in db");
        }
        AuthenticationToken newAuthenticationToken = authenticationRepository.save(authenticationToken);

        user.setAuthenticationToken(newAuthenticationToken);
        userRepository.save(user);

        return new AuthenticationTokenDto(accessToken, user.getId());
    }

    @Override
    public void logOut(String accessToken, UserId userId) {
        for (AuthenticationToken token: authenticationRepository.findAll()) {
            if (token.getAccessToken().equals(accessToken) &&
                    token.getUserId().getId().equals(userId.getUserId())) {
                User user = userRepository.findOne(userId.getUserId());
                user.setAuthenticationToken(null);
                authenticationRepository.delete(token);
            }
        }
    }

    @Override
    public void deleteUser(String accessToken, UserId userId) {
        userRepository.delete(userId.getUserId());
    }

    @Override
    public void checkToken(String accessToken, UserId userId) {
        if (accessToken == null) {
            throw new NullPointerException("Access token is null");
        }


        for (AuthenticationToken token: authenticationRepository.findAll()) {
            if (token.getAccessToken().equals(accessToken) &&
                    token.getUserId().getId().equals(userId.getUserId())) {
                return;
            }
        }

        throw new ValidationException("Validation data is invalid");
    }

}
