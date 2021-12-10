package com.officelibrary.library;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.officelibrary.library.exposure.model.Book;
import com.officelibrary.library.exposure.service.LibraryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;

@SpringBootTest
class LibraryServiceTest {

    private static final List<Book> library = Arrays.asList(
        new Book("Ulysses", "James Joyce", "Ulysses chronicles"),
        new Book("Don Quixote", "Miguel de Cervantes", "Retired country gentleman in his fifties"),
        new Book("One Hundred Years of Solitude", "Gabriel Garcia Marquez", "Widely beloved and acclaimed novel"),
        new Book("The Great Gatsby", "F. Scott Fitzgerald", "An era that Fitzgerald himself dubbed the.")
    );

    private LibraryService libraryService;

    @BeforeEach
    void reset() {
        libraryService = new LibraryService();
    }

    @Test
    void springVersionTest() {
        assertEquals("5.3.13", SpringVersion.getVersion());
    }

    @Test
    void addAndGetASingleBookTest() {
        libraryService.addBook(library.get(0));

        Optional<Book> bookOptional = libraryService.getBookByTitle("Ulysses");
        assertTrue(bookOptional.isPresent());
        Book book = bookOptional.get();
        assertAll(
            "Assert Ulysses is present",
            () -> assertEquals("Ulysses", book.getTitle()),
            () -> assertEquals(library.get(0).getAuthor(), book.getAuthor()),
            () -> assertEquals(library.get(0).getDescription(), book.getDescription(), "Descriptions must match")
        );
    }

    @Test
    void retrieveAbsentBookTest() {
        libraryService.addBook(library.get(0));

        Optional<Book> bookOptional = libraryService.getBookByTitle("Don Quixote");
        assertFalse(bookOptional.isPresent());
    }

    @Test
    void addMultipleBooksTest() {
        library.forEach(libraryService::addBook);

        assertEquals(4, libraryService.getBooks().size());
    }

    @Test
    void addBooksWithDuplicateTest() {
        library.forEach(libraryService::addBook);
        library.forEach(libraryService::addBook);
        assertEquals(8, libraryService.getBooks().size());
    }

    @Test
    void deleteABookTest() {
        library.forEach(libraryService::addBook);

        Book bookToDelete = library.get(2);
        libraryService.deleteBook(bookToDelete);

        List<Book> books = libraryService.getBooks();
        assertEquals(3, books.size());
        assertFalse(books.stream().anyMatch(b -> b.equals(bookToDelete)));
        assertEquals(3, library.stream().filter(books::contains).count());
    }
}
