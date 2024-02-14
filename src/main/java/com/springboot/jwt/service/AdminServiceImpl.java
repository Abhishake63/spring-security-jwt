package com.springboot.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.jwt.exception.NotFoundException;
import com.springboot.jwt.model.AdminEntity;
import com.springboot.jwt.repository.AdminRepo;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

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
    public AdminEntity save(AdminEntity adminEntity) {
        return adminRepo.save(adminEntity);
    }
}
