package com.officelibrary.library.exposure.model;

import java.util.Objects;
import java.util.UUID;

public class Author {

    private String id;
    private String name;
    private String surname;

    public Author(String name, String surname) {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.name = name;
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) &&
            Objects.equals(surname, author.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
