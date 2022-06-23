package com.officelibrary.persistence.model;

import java.util.Set;

public enum Role {

    ADMIN(Set.of(
        Privilege.ACTUATOR,
        Privilege.DELETE_ALL_CATEGORIES,
        Privilege.DELETE_ALL_AUTHORS,
        Privilege.DELETE_ALL_BOOKS)),
    MODERATOR(Set.of(
        Privilege.ADD_CATEGORY,
        Privilege.UPDATE_CATEGORY,
        Privilege.DELETE_CATEGORY,
        Privilege.ADD_AUTHOR,
        Privilege.UPDATE_AUTHOR,
        Privilege.DELETE_AUTHOR,
        Privilege.ADD_BOOK,
        Privilege.UPDATE_BOOK,
        Privilege.DELETE_BOOK)),
    CUSTOMER(Set.of(
        Privilege.READ_ALL_CATEGORIES,
        Privilege.READ_CATEGORY,
        Privilege.READ_ALL_AUTHORS,
        Privilege.READ_AUTHOR,
        Privilege.READ_ALL_BOOKS,
        Privilege.READ_BOOK));

    private final Set<Privilege> privileges;

    Role(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public String getName() {
        return "ROLE_" + name();
    }
}
