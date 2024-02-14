package com.springboot.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminAuthDto {

    private String username;
    private String password;
}
