package com.officelibrary.service;

import java.util.List;

import com.officelibrary.persistence.model.Author;
import com.officelibrary.persistence.repository.AuthorRepository;
import com.officelibrary.service.exceptions.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author get(String id) {
        return authorRepository.findById(id)
            .orElseThrow(AuthorNotFoundException::new);
    }

    public Author add(String name, String surname) {
        Author author = new Author(name, surname);
        authorRepository.save(author);
        return author;
    }

    public void remove(String id) {
        authorRepository.deleteById(id);
    }
}
