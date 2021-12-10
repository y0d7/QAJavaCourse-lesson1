package com.officelibrary.library;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.officelibrary.library.exposure.controller.BookController;
import com.officelibrary.library.exposure.model.Book;
import com.officelibrary.library.exposure.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    MockMvc mockMvc;

    // @Mock
    @MockBean
    LibraryService libraryService;

    private static final List<Book> library = Arrays.asList(
        new Book("Ulysses", "James Joyce", "Ulysses chronicles"),
        new Book("Don Quixote", "Miguel de Cervantes", "Retired country gentleman in his fifties"),
        new Book("One Hundred Years of Solitude", "Gabriel Garcia Marquez", "Widely beloved and acclaimed novel"),
        new Book("The Great Gatsby", "F. Scott Fitzgerald", "An era that Fitzgerald himself dubbed the.")
    );

    @Test
    void getAllBooks() throws Exception {
        Mockito.when(libraryService.getBooks()).thenReturn(library);

        mockMvc.perform(MockMvcRequestBuilders
            .get("/library/books")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(4)))
            .andExpect(jsonPath("$[2].title", is("One Hundred Years of Solitude")));
    }

    @Test
    void getBookByTitleParam() throws Exception {
        int bookId = 3;
        String bookTitle = "The Great Gatsby";
        Mockito.when(libraryService.getBookByTitle(bookTitle)).thenReturn(Optional.of(library.get(bookId)));

        mockMvc.perform(MockMvcRequestBuilders
            .get("/library/book")
            .queryParam("title", bookTitle)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is(bookTitle)))
            .andExpect(jsonPath("$.author", is("F. Scott Fitzgerald")))
            .andExpect(jsonPath("$.description", is("An era that Fitzgerald himself dubbed the.")));
    }

    @Test
    void getBookByTitleNotFound() throws Exception {
        String bookTitle = "The Great Gatsby";
        Mockito.when(libraryService.getBookByTitle(bookTitle)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
            .get("/library/book")
            .queryParam("title", bookTitle)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    void addABook() throws Exception {
        Book bookToAdd = library.get(0);
        Mockito.when(libraryService.addBook(bookToAdd)).thenReturn(Collections.singletonList(bookToAdd));

        mockMvc.perform(MockMvcRequestBuilders
            .post("/library/add-book")
            .content(mapToJson(bookToAdd))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void addABookBadRequest() throws Exception {
        Book bookToAdd = library.get(0);
        Mockito.when(libraryService.addBook(bookToAdd)).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders
            .post("/library/add-book")
            .content(mapToJson(bookToAdd))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }

    static String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }
}
