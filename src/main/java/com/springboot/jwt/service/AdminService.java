package com.springboot.jwt.service;

import com.springboot.jwt.model.AdminEntity;

public interface AdminService {

    AdminEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    AdminEntity save(AdminEntity adminEntity);
}
