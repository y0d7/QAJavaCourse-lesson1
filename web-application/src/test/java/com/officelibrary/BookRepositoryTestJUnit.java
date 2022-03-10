package com.officelibrary;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import com.officelibrary.persistence.model.Author;
import com.officelibrary.persistence.model.Book;
import com.officelibrary.persistence.model.Category;
import com.officelibrary.persistence.repository.BookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class BookRepositoryTestJUnit {

    private static final List<Book> library = Arrays.asList(
        new Book("Ulysses", "Ulysses chronicles", List.of(new Author("James", "Joyce")), new Category("Myths")),
        new Book("Don Quixote", "Retired country gentleman in his fifties", List.of(new Author("Miguel", "de Cervantes")),
            new Category("Legends")),
        new Book("One Hundred Years of Solitude", "Widely beloved and acclaimed novel", null, new Category("Magic Realism")),
        new Book("The Great Gatsby", "An era that Fitzgerald himself dubbed the.",
            List.of(new Author("Francis Scott", "Fitzgerald")), new Category("Thriller"))
    );

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void startUp() {
        bookRepository.saveAll(library);
    }

    @AfterEach
    public void cleanUp() {
        bookRepository.deleteAll();
    }

    @Test
    void should_returnNotEmptyList_whenTryingToGetDataFromDatabase() {
        assertNotNull(bookRepository.findAll());
        assertTrue(bookRepository.findAll().size() >= 1);
        assertFalse(bookRepository.findAll().isEmpty());
    }

    @Test
    void should_returnBookWithExpectedTitleAndDescription_whenBookIsFoundInDatabase() {
        //given
        String title = "Ulysses";
        //when
        List<Book> books = bookRepository.findAllByTitle(title);
        assertTrue(books.size() == 1);
        Book book = books.get(0);

        //then
        assertAll(
            "Assert Ulysses is present",
            () -> assertEquals("Ulysses", book.getTitle()),
            () -> assertEquals(library.get(0).getDescription(), book.getDescription(), "Descriptions must match")
        );
    }

    @Test
    void should_returnDuplicatedListOfBook_whenListIsSavedSecondTimeInDatabase() {
        //given
        //when
        bookRepository.saveAll(library);

        //then
        assertEquals(8, bookRepository.findAll().size());
    }

    @Test
    void should_deleteGivenBook_whenBookIsFoundInDatabase() {
        //given
        Book bookToDelete = bookRepository.findAllByTitle("Ulysses").get(0);
        assertNotNull(bookToDelete);

        //when
        bookRepository.delete(bookToDelete);

        //then
        List<Book> books = bookRepository.findAll();
        assertAll(
            "Assert Ulysses book has been deleted",
            () ->  assertEquals(3, books.size()),
            () -> assertFalse(books.stream().anyMatch(b -> b.equals(bookToDelete)))
        );
    }

}
