package com.officelibrary.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import com.officelibrary.persistence.model.Category;
import com.officelibrary.persistence.repository.CategoryRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final List<Category> categories = new ArrayList<>();

    @Override
    public <S extends Category> S save(S entity) {
        categories.add(entity);
        return entity;
    }

    @Override
    public <S extends Category> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Category> findById(String id) {
        return categories.stream()
            .filter(author -> author.getId().equals(id))
            .findFirst();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Category> findAll() {
        return Collections.unmodifiableList(categories);
    }

    @Override
    public Iterable<Category> findAllById(Iterable<String> iterableIds) {
        List<String> ids = StreamSupport.stream(iterableIds.spliterator(), false).toList();
        return categories.stream().filter(Category -> ids.contains(Category.getId())).toList();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(String id) {
        categories.removeIf(Category -> Category.getId().equals(id));
    }

    @Override
    public void delete(Category entity) {
        categories.removeIf(Category -> Category.getId().equals(entity.getId()));
    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Category> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Category> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> S insert(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> List<S> insert(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Category, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }
}
