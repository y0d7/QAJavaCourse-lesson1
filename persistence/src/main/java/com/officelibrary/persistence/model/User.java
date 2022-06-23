package com.officelibrary.persistence.model;

import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    private final String username;

    private final Set<Role> roles;

    private final String hashedPassword;
}
