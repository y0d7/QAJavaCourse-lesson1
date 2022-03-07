package com.officelibrary.controller.author.model;

import com.officelibrary.persistence.model.Author;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

    private String id;

    private String name;

    private String surname;

    public AuthorDto(Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.surname = author.getSurname();
    }
}
