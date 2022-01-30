package com.officelibrary.service;

import java.util.List;

import com.officelibrary.persistence.model.Category;
import com.officelibrary.persistence.repository.CategoryRepository;
import com.officelibrary.service.exceptions.CategoryAlreadyExistsException;
import com.officelibrary.service.exceptions.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category get(String id) {
        return categoryRepository.findById(id)
            .orElseThrow(CategoryNotFoundException::new);
    }

    public Category add(String name) {
        boolean categoryAlreadyExisting = categoryRepository.findAll()
            .stream()
            .anyMatch(category -> category.getName().equals(name));
        if (categoryAlreadyExisting) {
            throw new CategoryAlreadyExistsException();
        }
        Category category = new Category(name);
        categoryRepository.save(category);
        return category;
    }

    public void remove(String id) {
        if (categoryRepository.findById(id).isPresent()) {
            throw new CategoryNotFoundException();
        }
        categoryRepository.deleteById(id);
    }
}
