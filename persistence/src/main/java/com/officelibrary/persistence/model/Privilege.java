package com.officelibrary.persistence.model;

public enum Privilege {

    ACTUATOR,

    READ_ALL_CATEGORIES,
    READ_CATEGORY,
    ADD_CATEGORY,
    UPDATE_CATEGORY,
    DELETE_CATEGORY,
    DELETE_ALL_CATEGORIES,

    READ_ALL_AUTHORS,
    READ_AUTHOR,
    ADD_AUTHOR,
    UPDATE_AUTHOR,
    DELETE_AUTHOR,
    DELETE_ALL_AUTHORS,

    READ_ALL_BOOKS,
    READ_BOOK,
    ADD_BOOK,
    UPDATE_BOOK,
    DELETE_BOOK,
    DELETE_ALL_BOOKS,

    READ_ALL_USERS,
    READ_USER,
    ADD_USER,
    UPDATE_USER,
    DELETE_USER,
    DELETE_ALL_USERS;

    public String getName() {
        return name();
    }
}
