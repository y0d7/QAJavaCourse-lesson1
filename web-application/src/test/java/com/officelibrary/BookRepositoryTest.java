package com.officelibrary;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.officelibrary.persistence.model.Book;
import com.officelibrary.persistence.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void cleanUp() {
        bookRepository.deleteAll();
    }

    @Test
    public void shouldBeNotEmpty() {
        bookRepository.save(new Book());
        assertNotNull(bookRepository.findAll());
    }

}
