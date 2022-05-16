
package com.officelibrary.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import com.officelibrary.persistence.model.Author;
import com.officelibrary.persistence.repository.AuthorRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final List<Author> authors = new ArrayList<>();

    @Override
    public <S extends Author> S save(S entity) {
        authors.add(entity);
        return entity;
    }

    @Override
    public <S extends Author> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Author> findById(String id) {
        return authors.stream()
            .filter(author -> author.getId().equals(id))
            .findFirst();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Author> findAll() {
        return Collections.unmodifiableList(authors);
    }

    @Override
    public Iterable<Author> findAllById(Iterable<String> iterableIds) {
        List<String> ids = StreamSupport.stream(iterableIds.spliterator(), false).toList();
        return authors.stream().filter(Author -> ids.contains(Author.getId())).toList();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(String id) {
        authors.removeIf(Author -> Author.getId().equals(id));
    }

    @Override
    public void delete(Author entity) {
        authors.removeIf(Author -> Author.getId().equals(entity.getId()));
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Author> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Author> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> S insert(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> List<S> insert(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Author, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }
}
