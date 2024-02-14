package com.springboot.jwt.dto;

import com.springboot.jwt.model.AdminEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginResponseDto {

    private String token;
    private AdminEntity admin;
}
