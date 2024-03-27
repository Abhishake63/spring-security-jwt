package com.springboot.jwt.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.springboot.jwt.model.UserType;
import com.springboot.jwt.security.CustomUserDetailsService;

@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;

    AuthenticationService(AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    public Authentication authenticate(UserType userType, String userName, String password) {
        customUserDetailsService.setUserType(userType);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
