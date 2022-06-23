package com.officelibrary.controller.book.model;

import java.util.List;
import java.util.stream.Collectors;

import com.officelibrary.controller.author.model.AuthorDto;
import com.officelibrary.controller.category.model.CategoryDto;
import com.officelibrary.persistence.model.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {

    private String id;
    private String title;
    private String description;
    private List<AuthorDto> authors;
    private CategoryDto category;

    public BookDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.authors = book.getAuthors()
            .stream()
            .map(AuthorDto::new)
            .toList();
        this.category = new CategoryDto(book.getCategory());
    }
}
