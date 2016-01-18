package com.teamdev.controller;

import com.teamdev.dto.AuthenticationTokenDto;
import com.teamdev.dto.UserDto;

import com.teamdev.requestDto.LogInDto;
import com.teamdev.requestDto.wrappers.UserId;
import com.teamdev.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    @ResponseBody
    public UserId signUp(@RequestBody UserDto userDto) {
        return authenticationService.signUp(userDto);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public AuthenticationTokenDto login(@RequestBody LogInDto logInDto) {
        return authenticationService.logIn(logInDto.password, logInDto.name);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/check/{userid}", params = {"token"}, method = RequestMethod.GET)
    public void checkToken(@RequestParam String token, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        authenticationService.checkToken(token, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/logout/{userid}", params = {"token"}, method = RequestMethod.GET)
    public void logOut(@RequestParam String token, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        authenticationService.logOut(token, userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/delete/{userid}", params = {"token"}, method = RequestMethod.GET)
    public void deleteUser(@RequestParam String token, @PathVariable Long userid) {
        UserId userId = new UserId(userid);
        authenticationService.deleteUser(token, userId);
    }

}
