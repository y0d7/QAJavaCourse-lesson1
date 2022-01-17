package com.officelibrary.library.exposure.service;

import java.util.List;
import java.util.Optional;

import com.officelibrary.library.exposure.model.Book;
import com.officelibrary.library.exposure.repository.BookRepository;
import com.officelibrary.library.exposure.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@Service
@ApplicationScope
public class BookService {

    private BookRepository bookRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public BookService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public Book addBook(Book book) {
        categoryRepository.save(book.getCategory());
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<List<Book>> getBookByTitle(String title) {
        return Optional.ofNullable(bookRepository.findAllByTitle(title));
    }

    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    public void deleteBook(Book book) {
        bookRepository.delete(book);
    }

    public void deleteBookById(String id) {
        Optional<Book> book = getBookById(id);
        book.ifPresent(book1 -> bookRepository.delete(book1));
    }

    public void updateBook(String id, Book newBook) {
        Optional<Book> bookOptional = getBookById(id);
        bookOptional.ifPresent(book -> {
            categoryRepository.save(newBook.getCategory());
            book.updateFields(newBook);
            bookRepository.save(book);
        });
    }
}
