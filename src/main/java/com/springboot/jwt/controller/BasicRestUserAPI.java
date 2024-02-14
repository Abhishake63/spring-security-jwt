package com.springboot.jwt.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public/user")
public class BasicRestUserAPI {

    @GetMapping("/")
    public ResponseEntity<String> userHome(Authentication authentication) {
        return new ResponseEntity<String>("Ok", HttpStatus.OK);
    }
}
