package com.officelibrary.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import com.officelibrary.persistence.model.Book;
import com.officelibrary.persistence.repository.BookRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

public class BookRepositoryImpl implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    @Override
    public List<Book> findAllByTitle(String title) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> S save(S entity) {
        books.add(entity);
        return entity;
    }

    @Override
    public <S extends Book> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Book> findById(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Book> findAll() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public Iterable<Book> findAllById(Iterable<String> iterableIds) {
        List<String> ids = StreamSupport.stream(iterableIds.spliterator(), false).toList();
        return books.stream().filter(book -> ids.contains(book.getId())).toList();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(String id) {
        books.removeIf(book -> book.getId().equals(id));
    }

    @Override
    public void delete(Book entity) {
        books.removeIf(book -> book.getId().equals(entity.getId()));
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Book> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Book> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> S insert(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> List<S> insert(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Book, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }
}
