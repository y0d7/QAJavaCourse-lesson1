package com.officelibrary.controller.book;

import java.util.List;

import javax.validation.Valid;

import com.officelibrary.controller.book.model.BookDto;
import com.officelibrary.controller.book.model.PostBookDto;
import com.officelibrary.controller.book.model.PutBookDto;
import com.officelibrary.persistence.model.Book;
import com.officelibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.getAll()
            .stream()
            .map(BookDto::new)
            .toList();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") String id) {
        Book book = bookService.get(id);
        return new BookDto(book);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        bookService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto add(@RequestBody @Valid PostBookDto postBook) {
        Book book = bookService.add(
            postBook.getTitle(),
            postBook.getDescription(),
            postBook.getAuthorIds(),
            postBook.getCategoryId());
        return new BookDto(book);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable("id") String id, @RequestBody PutBookDto putBook) {
        Book book = bookService.update(
            id,
            putBook.getTitle(),
            putBook.getDescription(),
            putBook.getAuthorIds(),
            putBook.getCategoryId());
        return new BookDto(book);
    }
}
