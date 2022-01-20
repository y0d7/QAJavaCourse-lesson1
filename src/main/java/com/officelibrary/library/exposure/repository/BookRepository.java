package com.officelibrary.library.exposure.repository;

import java.util.List;

import com.officelibrary.library.exposure.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByTitle(String title);

}
