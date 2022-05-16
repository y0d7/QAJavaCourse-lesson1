package com.officelibrary.controller;

import java.util.Set;

import com.officelibrary.persistence.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserDetailsConfig {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = createUserDetails(
            "myAdmin",
            "password1",
            Set.of(Role.ADMIN, Role.MODERATOR, Role.CUSTOMER));
        UserDetails moderator = createUserDetails(
            "myModerator",
            "password2",
            Set.of(Role.MODERATOR, Role.CUSTOMER));
        UserDetails customer = createUserDetails(
            "myCustomer",
            "password3",
            Set.of(Role.CUSTOMER));
        return new InMemoryUserDetailsManager(admin, moderator, customer);
    }

    private UserDetails createUserDetails(String username, String password, Set<Role> roles) {
        String[] stringRoles = roles.stream()
            .map(Enum::name)
            .toArray(String[]::new);
        GrantedAuthority[] authorities = roles.stream()
            .flatMap(role -> role.getPrivileges().stream())
            .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
            .toArray(SimpleGrantedAuthority[]::new);
        return User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles(stringRoles)
            .authorities(authorities)
            .build();
    }
}
