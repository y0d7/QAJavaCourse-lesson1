package com.officelibrary.library.exposure.repository;

import com.officelibrary.library.exposure.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

}
