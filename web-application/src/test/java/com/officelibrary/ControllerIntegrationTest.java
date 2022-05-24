package com.officelibrary;

import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.officelibrary.config.IntegrationTestConfig;
import com.officelibrary.controller.author.AuthorController;
import com.officelibrary.controller.author.model.PostAuthorDto;
import com.officelibrary.controller.book.BookController;
import com.officelibrary.controller.book.model.PostBookDto;
import com.officelibrary.controller.category.CategoryController;
import com.officelibrary.controller.category.model.PostCategoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = {
    BookController.class,
    AuthorController.class,
    CategoryController.class})
@Import(IntegrationTestConfig.class)
class ControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddCategory() throws Exception {
        // given
        PostCategoryDto postCategory = new PostCategoryDto();
        String categoryName = "Horror" + UUID.randomUUID();
        postCategory.setName(categoryName);
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/categories")
                .header(HttpHeaders.AUTHORIZATION, buildHttpBasicAuthHeaderValue())
                .content(asJsonString(postCategory))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", hasLength(36)))
            .andExpect(jsonPath("$.name", is(categoryName)));
    }

    @Test
    void shouldAddAuthor() throws Exception {
        // given
        PostAuthorDto postAuthor = new PostAuthorDto();
        postAuthor.setName("Bram");
        postAuthor.setSurname("Stoker");
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/authors")
                .header(HttpHeaders.AUTHORIZATION, buildHttpBasicAuthHeaderValue())
                .content(asJsonString(postAuthor))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", hasLength(36)))
            .andExpect(jsonPath("$.name", is("Bram")))
            .andExpect(jsonPath("$.surname", is("Stoker")));
    }

    @Test
    void shouldAddBookWithCategoryAndAuthor() throws Exception {
        // given
        String categoryId = addCategory();
        String authorId = addAuthor();
        PostBookDto postBook = new PostBookDto();
        postBook.setTitle("Dracula");
        postBook.setDescription("The story of the fight against the vampire Dracula in an epistolary format");
        postBook.setAuthorIds(List.of(authorId));
        postBook.setCategoryId(categoryId);
        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/books")
                .header(HttpHeaders.AUTHORIZATION, buildHttpBasicAuthHeaderValue())
                .content(asJsonString(postBook))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", hasLength(36)))
            .andExpect(jsonPath("$.title", is("Dracula")))
            .andExpect(jsonPath("$.description", is("The story of the fight against the vampire Dracula in an epistolary format")))
            .andExpect(jsonPath("$.authors", hasSize(1)))
            .andExpect(jsonPath("$.authors[0].id", is(authorId)))
            .andExpect(jsonPath("$.authors[0].name", is("Bram")))
            .andExpect(jsonPath("$.authors[0].surname", is("Stoker")))
            .andExpect(jsonPath("$.category.id", is(categoryId)))
            .andExpect(jsonPath("$.category.name", is("Horror")));
    }

    private String addCategory() throws Exception {
        PostCategoryDto postCategory = new PostCategoryDto();
        postCategory.setName("Horror");
        MvcResult categoryResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/categories")
                .header(HttpHeaders.AUTHORIZATION, buildHttpBasicAuthHeaderValue())
                .content(asJsonString(postCategory))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andReturn();
        return JsonPath.read(categoryResult.getResponse().getContentAsString(), "$.id");
    }

    private String addAuthor() throws Exception {
        PostAuthorDto postAuthor = new PostAuthorDto();
        postAuthor.setName("Bram");
        postAuthor.setSurname("Stoker");
        MvcResult authorResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/authors")
                .header(HttpHeaders.AUTHORIZATION, buildHttpBasicAuthHeaderValue())
                .content(asJsonString(postAuthor))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", hasLength(36)))
            .andReturn();
        return JsonPath.read(authorResult.getResponse().getContentAsString(), "$.id");
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String buildHttpBasicAuthHeaderValue() {
        return "Basic " + Base64Utils.encodeToString("admin1:admin11".getBytes());
    }
}