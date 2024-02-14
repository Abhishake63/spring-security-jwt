package com.springboot.jwt.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthDto {

    private String username;
    private String password;
}
