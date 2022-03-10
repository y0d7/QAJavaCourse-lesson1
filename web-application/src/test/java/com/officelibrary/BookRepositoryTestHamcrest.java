package com.officelibrary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class BookRepositoryTestHamcrest {

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
    public void should_returnNotEmptyList_whenTryingToGetDataFromDatabase() {
        assertThat(bookRepository.findAll(), notNullValue());
    }

    @Test
    void should_returnBookWithExpectedTitleAndDescription_whenBookIsFoundInDatabase() {
        //given
        //when
        List<Book> books = bookRepository.findAllByTitle("Ulysses");
        assertThat(books, hasSize(1));
        Book book = books.get(0);

        //then
        assertThat(book.getTitle(), equalTo("Ulysses"));
        assertThat("Descriptions must match", book.getDescription(), equalTo(library.get(0).getDescription()));
    }

    @Test
    void should_returnDuplicatedListOfBook_whenListIsSavedSecondTimeInDatabase() {
        //given
        //when
        bookRepository.saveAll(library);

        //then
        assertThat(bookRepository.findAll(), hasSize(8));
    }

    @Test
    void should_deleteGivenBook_whenBookIsFoundInDatabase() {
        //given
        Book bookToDelete = bookRepository.findAllByTitle("Ulysses").get(0);
        assertThat(bookToDelete, notNullValue());

        //when
        bookRepository.delete(bookToDelete);

        //then
        assertThat("Assert Ulysses book has been deleted",
            bookRepository.findAll(), allOf(hasSize(3), not(hasItem(bookToDelete))));
    }

}
