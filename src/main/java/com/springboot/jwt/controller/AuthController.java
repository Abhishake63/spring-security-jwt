package com.springboot.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jwt.dto.UserAuthDto;
import com.springboot.jwt.dto.UserLoginResponseDto;
import com.springboot.jwt.exception.BadRequestException;
import com.springboot.jwt.model.UserEntity;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.security.CustomUserDetailsService;
import com.springboot.jwt.security.JwtGenerator;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public String userRegister(@RequestBody UserAuthDto userAuthDto) {

        if (userService.existsByUsername(userAuthDto.getUsername())) {
            throw new BadRequestException("Email is already registered !!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userAuthDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        userService.save(userEntity);
        return "User Register successfull !!";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public UserLoginResponseDto userLogin(@RequestBody UserAuthDto userAuthDto) {

        authenticate(UserType.USER, userAuthDto.getUsername(), userAuthDto.getPassword());
        String token = jwtGenerator.generateToken(userAuthDto.getUsername(), UserType.USER.toString());
        return setUserLoginSuccessResponse(userAuthDto.getUsername(), token);
    }

    private UserLoginResponseDto setUserRegisterSuccessResponse(UserEntity userEntity, String token) {
        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("Registration successful !!");
        responseDto.setToken(token);
        responseDto.setUser(userEntity);
        return responseDto;
    }

    private UserLoginResponseDto setUserLoginSuccessResponse(String username, String token) {
        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("login successful !!");
        responseDto.setToken(token);
        UserEntity user = userService.findByUsername(username);
        responseDto.setUser(user);
        return responseDto;
    }

    private Authentication authenticate(UserType userType, String userName, String password) {
        customUserDetailsService.setUserType(userType);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
