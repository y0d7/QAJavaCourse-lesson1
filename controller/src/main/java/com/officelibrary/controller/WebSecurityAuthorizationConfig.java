package com.officelibrary.controller;

import com.officelibrary.persistence.model.Privilege;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityAuthorizationConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority(Privilege.ACTUATOR.name())
            .antMatchers(HttpMethod.GET, "/api/books").hasAuthority(Privilege.READ_ALL_BOOKS.name())
            .antMatchers(HttpMethod.GET, "/api/books/{id}").hasAuthority(Privilege.READ_BOOK.name())
            .antMatchers(HttpMethod.DELETE, "/api/books/{id}").hasAuthority(Privilege.DELETE_BOOK.name())
            .antMatchers(HttpMethod.POST, "/api/books").hasAuthority(Privilege.ADD_BOOK.name())
            .antMatchers(HttpMethod.PUT, "/api/books/{id}").hasAuthority(Privilege.UPDATE_BOOK.name())
            .antMatchers(HttpMethod.GET, "/api/categories").hasAuthority(Privilege.READ_ALL_CATEGORIES.name())
            .antMatchers(HttpMethod.GET, "/api/categories/{id}").hasAuthority(Privilege.READ_CATEGORY.name())
            .antMatchers(HttpMethod.DELETE, "/api/categories/{id}").hasAuthority(Privilege.DELETE_CATEGORY.name())
            .antMatchers(HttpMethod.POST, "/api/categories").hasAuthority(Privilege.ADD_CATEGORY.name())
            .antMatchers(HttpMethod.PUT, "/api/categories/{id}").hasAuthority(Privilege.UPDATE_CATEGORY.name())
            .antMatchers(HttpMethod.GET, "/api/authors").hasAuthority(Privilege.READ_ALL_AUTHORS.name())
            .antMatchers(HttpMethod.GET, "/api/authors/{id}").hasAuthority(Privilege.READ_AUTHOR.name())
            .antMatchers(HttpMethod.DELETE, "/api/authors/{id}").hasAuthority(Privilege.DELETE_AUTHOR.name())
            .antMatchers(HttpMethod.POST, "/api/authors").hasAuthority(Privilege.ADD_AUTHOR.name())
            .antMatchers(HttpMethod.PUT, "/api/authors/{id}").hasAuthority(Privilege.UPDATE_AUTHOR.name())
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }
}
