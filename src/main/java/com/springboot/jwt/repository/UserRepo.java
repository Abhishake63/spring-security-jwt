package com.springboot.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.jwt.model.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
