package com.officelibrary.library.exposure.model;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
public class Book {

    @Id
    private String uniqueID;
    private String title;
    private String description;
    private List<Author> authors;

    @DocumentReference(lazy = true)
    private Category category;

    public Book(String title, String description, List<Author> authors, Category category) {
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.category = category;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getCategoryName() {
        if (this.category == null) {
            return "";
        }
        return this.category.getName();
    }

    public void updateFields(Book book) {
        this.title = book.title;
        this.authors = book.authors;
        this.description = book.description;
        this.category = book.category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Book book = (Book) o;
        return Objects.equals(title, book.title) && Objects.equals(description, book.description) &&
            Objects.equals(authors, book.authors) && Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, authors, category);
    }
}
