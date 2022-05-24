package com.officelibrary.service;

public interface UserPasswordHasher {

    String hash(String password);
}
