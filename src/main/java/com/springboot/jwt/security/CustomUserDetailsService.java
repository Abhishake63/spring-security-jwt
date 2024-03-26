package com.springboot.jwt.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.jwt.model.AdminEntity;
import com.springboot.jwt.model.UserEntity;
import com.springboot.jwt.model.UserType;
import com.springboot.jwt.service.AdminService;
import com.springboot.jwt.service.UserService;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class CustomUserDetailsService implements UserDetailsService {

    private UserType userType;
    private AdminService adminService;
    private UserService userService;

    CustomUserDetailsService(AdminService adminService, UserService userService) {
        this.adminService = adminService;
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userType == UserType.ADMIN) {
            AdminEntity adminEntity = adminService.findByUsername(username);
            SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority(UserType.ADMIN.toString());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(adminAuthority);
            return new User(adminEntity.getUsername(), adminEntity.getPassword(), authorities);

        } else if (userType == UserType.USER) {
            UserEntity user = userService.findByUsername(username);
            SimpleGrantedAuthority userAuthority = new SimpleGrantedAuthority(UserType.USER.toString());
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(userAuthority);
            return new User(user.getUsername(), user.getPassword(), authorities);
        }
        return null;
    }
}
