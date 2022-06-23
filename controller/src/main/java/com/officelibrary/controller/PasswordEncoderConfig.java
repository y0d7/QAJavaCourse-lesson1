package com.officelibrary.controller;

import com.officelibrary.service.UserPasswordHasher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(15);
    private static final UserPasswordHasher USER_PASSWORD_HASHER = ENCODER::encode;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return ENCODER;
    }

    @Bean
    public UserPasswordHasher userPasswordHasher() {
        return USER_PASSWORD_HASHER;
    }
}
