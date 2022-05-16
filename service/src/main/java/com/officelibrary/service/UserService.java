package com.officelibrary.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.officelibrary.persistence.model.Role;
import com.officelibrary.persistence.model.User;
import com.officelibrary.persistence.repository.UserRepository;
import com.officelibrary.service.exceptions.UserAlreadyExistsException;
import com.officelibrary.service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllModerators() {
        return userRepository.findAll()
            .stream()
            .filter(user -> user.getRoles().contains(Role.MODERATOR))
            .toList();
    }

    public List<User> getAllCustomers() {
        return userRepository.findAll()
            .stream()
            .filter(user -> user.getRoles().contains(Role.CUSTOMER))
            .toList();
    }

    public User get(String id) {
        return userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
    }

    public User addModerator(String username, String password) {
        return addUser(username, password, Set.of(Role.MODERATOR, Role.CUSTOMER));
    }

    public User addCustomer(String username, String password) {
        return addUser(username, password, Set.of(Role.CUSTOMER));
    }

    public void remove(String id) {
        if (userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername();
    }

    private User addUser(String username, String password, Set<Role> roles) {
        boolean userAlreadyExisting = userRepository.findAll()
            .stream()
            .anyMatch(user -> user.getUsername().equals(username));
        if (userAlreadyExisting) {
            throw new UserAlreadyExistsException();
        }
        String hashedPassword = hashPassword(password);
        User user = new User(username, roles, hashedPassword);
        userRepository.save(user);
        return user;
    }

    private String hashPassword(String password) {
        byte[] passwordInBytes = password.getBytes(StandardCharsets.UTF_8);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        byte[] hashedPassword = md.digest(passwordInBytes);
        return new String(hashedPassword);
    }
}
