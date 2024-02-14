package com.springboot.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jwt.dto.UserAuthDto;
import com.springboot.jwt.dto.UserLoginResponseDto;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.security.CustomUserDetailsService;
import com.springboot.jwt.service.UserService;

@RestController
@RequestMapping("/auth/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public String userRegister(@RequestBody UserAuthDto userAuthDto) {
        return userService.regUser(userAuthDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UserLoginResponseDto userLogin(@RequestBody UserAuthDto userAuthDto) {
        authenticate(UserType.USER, userAuthDto.getUsername(), userAuthDto.getPassword());
        return userService.loginUser(userAuthDto);
    }

    private Authentication authenticate(UserType userType, String userName, String password) {
        customUserDetailsService.setUserType(userType);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
