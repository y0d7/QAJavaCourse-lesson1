package com.officelibrary.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.officelibrary.persistence.model.Author;
import com.officelibrary.persistence.model.Book;
import com.officelibrary.persistence.model.Category;
import com.officelibrary.persistence.repository.AuthorRepository;
import com.officelibrary.persistence.repository.BookRepository;
import com.officelibrary.persistence.repository.CategoryRepository;
import com.officelibrary.service.exceptions.BookNotCreatedException;
import com.officelibrary.service.exceptions.BookNotFoundException;
import com.officelibrary.service.exceptions.BookNotUpdatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    private final AuthorRepository authorRepository;

    public Book add(String title,
                    String description,
                    List<String> authorIds,
                    String categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(BookNotCreatedException::new);
        List<Author> authors = Lists.newArrayList(authorRepository.findAllById(authorIds));
        boolean allAuthorsExisting = authorIds.stream()
            .allMatch(id -> authors.stream().anyMatch(author -> author.getId().equals(id)));
        if (!allAuthorsExisting) {
            throw new BookNotCreatedException();
        }
        Book book = new Book(title, description, authors, category);
        bookRepository.save(book);
        return book;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book get(String id) {
        return getOrThrowNotFound(id);
    }

    public void delete(String id) {
        Book book = getOrThrowNotFound(id);
        bookRepository.delete(book);
    }

    public Book update(String id,
                       String title,
                       String description,
                       List<String> authorIds,
                       String categoryId) {
        Book book = getOrThrowNotFound(id);
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(BookNotUpdatedException::new);
        List<Author> authors = Lists.newArrayList(authorRepository.findAllById(authorIds));
        book.update(
            title,
            description,
            authors,
            category);
        bookRepository.save(book);
        return book;
    }

    private Book getOrThrowNotFound(String id) {
        return bookRepository.findById(id)
            .orElseThrow(BookNotFoundException::new);
    }
}
