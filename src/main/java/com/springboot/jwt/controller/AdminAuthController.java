package com.springboot.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.jwt.dto.AdminAuthDto;
import com.springboot.jwt.dto.AdminLoginResponseDto;
import com.springboot.jwt.exception.BadRequestException;
import com.springboot.jwt.model.AdminEntity;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.security.CustomUserDetailsService;
import com.springboot.jwt.security.JwtGenerator;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    public String adminRegister(@RequestBody AdminAuthDto adminAuthDto) {

        if (adminService.existsByUsername(adminAuthDto.getUsername())) {
            throw new BadRequestException("Username is taken !!");
        }
        saveAdminUser(adminAuthDto);

        return "Admin Register successfull !!";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminLoginResponseDto adminLogin(@RequestBody AdminAuthDto adminAuthDto) {

        authenticate(UserType.ADMIN, adminAuthDto.getUsername(), adminAuthDto.getPassword());
        String token = jwtGenerator.generateToken(adminAuthDto.getUsername(), UserType.ADMIN.toString());
        AdminLoginResponseDto responseDto = setAdminLoginResponse(adminAuthDto, token);

        return responseDto;
    }

    private Authentication authenticate(UserType userType, String userName, String password) {
        customUserDetailsService.setUserType(userType);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private void saveAdminUser(AdminAuthDto adminAuthDto) {
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setUsername(adminAuthDto.getUsername());
        adminEntity.setPassword(passwordEncoder.encode(adminAuthDto.getPassword()));
        adminService.save(adminEntity);
    }

    private AdminLoginResponseDto setAdminLoginResponse(AdminAuthDto adminAuthDto, String token) {
        AdminLoginResponseDto responseDto = new AdminLoginResponseDto();
        responseDto.setSuccess(true);
        responseDto.setMessage("login successful !!");
        responseDto.setToken(token);
        AdminEntity admin = adminService.findByUsername(adminAuthDto.getUsername());
        responseDto.setAdmin(admin);
        return responseDto;
    }
}
