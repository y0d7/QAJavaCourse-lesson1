package com.officelibrary.controller.category;

import java.util.List;
import java.util.stream.Collectors;

import com.officelibrary.controller.category.model.CategoryDto;
import com.officelibrary.controller.category.model.PostCategoryDto;
import com.officelibrary.persistence.model.Category;
import com.officelibrary.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.getAll()
            .stream()
            .map(CategoryDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public CategoryDto get(@PathVariable("id") String id) {
        Category category = categoryService.get(id);
        return new CategoryDto(category);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        categoryService.remove(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto add(@RequestBody PostCategoryDto postCategory) {
        Category category = categoryService.add(postCategory.getName());
        return new CategoryDto(category);
    }
}
