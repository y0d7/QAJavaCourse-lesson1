package com.officelibrary.persistence.model;

import java.util.Set;

public enum Role {

    ADMIN(Set.of(
        Privilege.ACTUATOR,
        Privilege.DELETE_ALL_CATEGORIES)),
    MODERATOR(Set.of(
        Privilege.ADD_CATEGORY,
        Privilege.UPDATE_CATEGORY,
        Privilege.DELETE_CATEGORY)
    ),
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
}
