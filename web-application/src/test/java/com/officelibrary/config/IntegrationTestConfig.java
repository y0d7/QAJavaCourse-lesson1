package com.officelibrary.config;

import java.util.Set;

import com.officelibrary.persistence.model.Role;
import com.officelibrary.persistence.repository.AuthorRepository;
import com.officelibrary.persistence.repository.BookRepository;
import com.officelibrary.persistence.repository.CategoryRepository;
import com.officelibrary.service.AuthorService;
import com.officelibrary.service.BookService;
import com.officelibrary.service.CategoryService;
import com.officelibrary.service.UserPasswordHasher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class IntegrationTestConfig {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(15);
    private static final UserPasswordHasher USER_PASSWORD_HASHER = ENCODER::encode;

    @Bean
    CategoryService categoryService(CategoryRepository categoryRepository) {
        return new CategoryService(categoryRepository);
    }

    @Bean
    AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorService(authorRepository);
    }

    @Bean
    BookService bookService(
        BookRepository bookRepository,
        CategoryRepository categoryRepository,
        AuthorRepository authorRepository) {
        return new BookService(bookRepository, categoryRepository, authorRepository);
    }

    @Bean
    BookRepository bookRepository() {
        return new BookRepositoryImpl();
    }

    @Bean
    CategoryRepository categoryRepository() {
        return new CategoryRepositoryImpl();
    }

    @Bean
    AuthorRepository authorRepository() {
        return new AuthorRepositoryImpl();
    }

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
            .password(ENCODER.encode(password))
            .roles(stringRoles)
            .authorities(authorities)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return ENCODER;
    }

    @Bean
    public UserPasswordHasher userPasswordHasher() {
        return USER_PASSWORD_HASHER;
    }
}
