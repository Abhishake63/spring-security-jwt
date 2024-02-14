package com.springboot.jwt.service;

import com.springboot.jwt.dto.UserAuthDto;
import com.springboot.jwt.dto.UserLoginResponseDto;
import com.springboot.jwt.model.UserEntity;

public interface UserService {

    UserEntity findById(Long id);
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);

    String regUser(UserAuthDto userAuthDto);
    UserLoginResponseDto loginUser(UserAuthDto userAuthDto);
}
