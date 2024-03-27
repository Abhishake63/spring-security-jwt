package com.springboot.jwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.jwt.dto.AdminAuthDto;
import com.springboot.jwt.dto.AdminLoginResponseDto;
import com.springboot.jwt.exception.BadRequestException;
import com.springboot.jwt.exception.NotFoundException;
import com.springboot.jwt.model.AdminEntity;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.repository.AdminRepo;
import com.springboot.jwt.security.JwtGenerator;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepo adminRepo;
    private PasswordEncoder passwordEncoder;
    private JwtGenerator jwtGenerator;

    AdminServiceImpl(AdminRepo adminRepo, PasswordEncoder passwordEncoder, JwtGenerator jwtGenerator) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public AdminEntity findByUsername(String username) {
        return adminRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Admin not found"));
    }

    @Override
    public Boolean existsByUsername(String username) {
        return adminRepo.existsByUsername(username);
    }

    @Override
    public String regAdmin(AdminAuthDto adminAuthDto) {
        if (existsByUsername(adminAuthDto.getUsername())) {
            throw new BadRequestException("Username is taken !!");
        }
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setUsername(adminAuthDto.getUsername());
        adminEntity.setPassword(passwordEncoder.encode(adminAuthDto.getPassword()));
        adminRepo.save(adminEntity);
        return "Admin Register successfull !!";
    }

    @Override
    public AdminLoginResponseDto loginAdmin(AdminAuthDto adminAuthDto) {
        String token = jwtGenerator.generateToken(adminAuthDto.getUsername(), UserType.ADMIN.toString());
        AdminEntity admin = findByUsername(adminAuthDto.getUsername());

        AdminLoginResponseDto responseDto = new AdminLoginResponseDto();
        responseDto.setToken(token);
        responseDto.setAdmin(admin);
        return responseDto;
    }
}
