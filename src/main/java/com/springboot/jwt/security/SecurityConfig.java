package com.springboot.jwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.jwt.model.UserType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthEntryPoint jwtAuthEntryPoint;

	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(jwtAuthEntryPoint)
                )
        .sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
        .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/public/admin/**").hasAuthority(UserType.ADMIN.toString())
                .requestMatchers("/api/public/user/**").hasAuthority(UserType.USER.toString())
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                )
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
}
