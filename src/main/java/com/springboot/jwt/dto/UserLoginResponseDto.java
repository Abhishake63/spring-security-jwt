package com.springboot.jwt.dto;

import com.springboot.jwt.model.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {

    private boolean success;
    private String message;
    private String token;
    private UserEntity user;
}
