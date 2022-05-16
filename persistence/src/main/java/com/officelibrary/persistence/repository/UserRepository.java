package com.officelibrary.persistence.repository;

import java.util.Optional;

import com.officelibrary.persistence.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername();
}
