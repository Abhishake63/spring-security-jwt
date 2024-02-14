package com.springboot.jwt.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    private UserType userType;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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
