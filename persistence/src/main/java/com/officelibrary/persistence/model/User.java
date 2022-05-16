package com.officelibrary.persistence.model;

import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class User {

    @Id
    private String id;

    private String username;

    private Set<Role> roles;

    private String hashedPassword;

    public User(String username, Set<Role> roles, String hashedPassword) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.roles = roles;
        this.hashedPassword = hashedPassword;
    }
}
