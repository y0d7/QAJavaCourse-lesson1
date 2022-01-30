package com.officelibrary.controller.author;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.officelibrary.controller.author.model.AuthorDto;
import com.officelibrary.controller.author.model.PostAuthorDto;
import com.officelibrary.persistence.model.Author;
import com.officelibrary.service.AuthorService;
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
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorDto> getAll() {
        return authorService.getAll()
            .stream()
            .map(AuthorDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public AuthorDto get(@PathVariable("id") String id) {
        Author author = authorService.get(id);
        return new AuthorDto(author);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto add(@RequestBody @Valid PostAuthorDto postAuthor) {
        Author author = authorService.add(postAuthor.getName(), postAuthor.getSurname());
        return new AuthorDto(author);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        authorService.remove(id);
    }
}
