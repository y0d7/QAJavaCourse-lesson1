package com.officelibrary.controller.book.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutBookDto {

    private String title;

    private String description;

    private List<String> authorIds;

    private String categoryId;
}
