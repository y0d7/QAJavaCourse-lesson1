package com.officelibrary.config;

import com.officelibrary.persistence.repository.AuthorRepository;
import com.officelibrary.persistence.repository.BookRepository;
import com.officelibrary.persistence.repository.CategoryRepository;
import com.officelibrary.service.AuthorService;
import com.officelibrary.service.BookService;
import com.officelibrary.service.CategoryService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class IntegrationTestConfig {

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
}
