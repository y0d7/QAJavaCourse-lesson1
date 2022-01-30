package com.officelibrary.persistence.model;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@Setter
public class Book {

    @Id
    private String id;

    private String title;

    private String description;

    @DocumentReference(lazy = true)
    private List<Author> authors;

    @DocumentReference(lazy = true)
    private Category category;

    public Book(String title, String description, List<Author> authors, Category category) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.category = category;
    }

    public void update(String title, String description, List<Author> authors, Category category) {
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.category = category;
    }
}
