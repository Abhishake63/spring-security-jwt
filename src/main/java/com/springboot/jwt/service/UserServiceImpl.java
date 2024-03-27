package com.springboot.jwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.jwt.dto.UserAuthDto;
import com.springboot.jwt.dto.UserLoginResponseDto;
import com.springboot.jwt.exception.BadRequestException;
import com.springboot.jwt.exception.NotFoundException;
import com.springboot.jwt.model.UserEntity;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.repository.UserRepo;
import com.springboot.jwt.security.JwtGenerator;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

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
    public String regUser(UserAuthDto userAuthDto) {
        if (existsByUsername(userAuthDto.getUsername())) {
            throw new BadRequestException("Username is already registered !!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userAuthDto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        userRepo.save(userEntity);
        return "User Register successfull !!";
    }

    @Override
    public UserLoginResponseDto loginUser(UserAuthDto userAuthDto) {
        String token = jwtGenerator.generateToken(userAuthDto.getUsername(), UserType.USER.toString());
        UserEntity user = findByUsername(userAuthDto.getUsername());

        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setToken(token);
        responseDto.setUser(user);
        return responseDto;
    }
}
