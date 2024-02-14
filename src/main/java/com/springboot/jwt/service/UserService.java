package com.springboot.jwt.service;

import com.springboot.jwt.model.UserEntity;

public interface UserService {

    UserEntity findById(Long id);
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
    UserEntity save(UserEntity userEntity);
}
