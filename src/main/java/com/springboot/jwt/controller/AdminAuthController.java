package com.springboot.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jwt.dto.AdminAuthDto;
import com.springboot.jwt.dto.AdminLoginResponseDto;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.security.CustomUserDetailsService;
import com.springboot.jwt.service.AdminService;

@RestController
@RequestMapping("/auth/admin")
public class AdminAuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public String adminRegister(@RequestBody AdminAuthDto adminAuthDto) {
        return adminService.regAdmin(adminAuthDto);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminLoginResponseDto adminLogin(@RequestBody AdminAuthDto adminAuthDto) {
        authenticate(UserType.ADMIN, adminAuthDto.getUsername(), adminAuthDto.getPassword());
        return adminService.loginAdmin(adminAuthDto);
    }

    private Authentication authenticate(UserType userType, String userName, String password) {
        customUserDetailsService.setUserType(userType);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}