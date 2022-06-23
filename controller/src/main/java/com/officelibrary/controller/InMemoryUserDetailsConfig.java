package com.officelibrary.controller;

import java.util.Set;

import com.officelibrary.persistence.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * The configuration below should be used
 * if you want to have predefined list of users stored in memory
 * for the purposes of local testing
 */
//@Configuration
@RequiredArgsConstructor
public class InMemoryUserDetailsConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = createUserDetails(
            "admin1",
            "admin11",
            Set.of(Role.ADMIN, Role.MODERATOR, Role.CUSTOMER));
        UserDetails moderator = createUserDetails(
            "moderator1",
            "moderator11",
            Set.of(Role.MODERATOR, Role.CUSTOMER));
        UserDetails customer = createUserDetails(
            "customer1",
            "customer11",
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
