package com.springboot.jwt.service;

import com.springboot.jwt.dto.AdminAuthDto;
import com.springboot.jwt.dto.AdminLoginResponseDto;
import com.springboot.jwt.model.AdminEntity;

public interface AdminService {

    AdminEntity findByUsername(String username);
    Boolean existsByUsername(String username);

    String regAdmin(AdminAuthDto adminAuthDto);
    AdminLoginResponseDto loginAdmin(AdminAuthDto adminAuthDto);
}
