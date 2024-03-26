package com.springboot.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jwt.dto.UserAuthDto;
import com.springboot.jwt.dto.UserLoginResponseDto;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.service.AuthenticationService;
import com.springboot.jwt.service.UserService;

@RestController
@RequestMapping("/auth/user")
public class AuthController {

    private UserService userService;
    private AuthenticationService authenticationService;

    AuthController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public String userRegister(@RequestBody UserAuthDto userAuthDto) {
        return userService.regUser(userAuthDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UserLoginResponseDto userLogin(@RequestBody UserAuthDto userAuthDto) {
        authenticationService.authenticate(UserType.USER, userAuthDto.getUsername(), userAuthDto.getPassword());
        return userService.loginUser(userAuthDto);
    }
}
