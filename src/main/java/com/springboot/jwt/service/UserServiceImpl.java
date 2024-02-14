package com.springboot.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.jwt.exception.NotFoundException;
import com.springboot.jwt.model.UserEntity;
import com.springboot.jwt.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserEntity findById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepo.save(userEntity);
    }
}
