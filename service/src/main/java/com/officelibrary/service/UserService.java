package com.officelibrary.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.officelibrary.persistence.model.Role;
import com.officelibrary.persistence.model.User;
import com.officelibrary.persistence.repository.UserRepository;
import com.officelibrary.service.exceptions.UserAlreadyExistsException;
import com.officelibrary.service.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserPasswordHasher userPasswordHasher;

    public void addAdmin() {
        boolean adminAlreadyPresent = userRepository.findAll()
            .stream()
            .anyMatch(user -> user.getRoles().contains(Role.ADMIN));
        if (adminAlreadyPresent) {
            log.info("Admin user already exists in repository");
        } else {
            log.info("Adding admin to repository with default username and password");
            addUser("admin1", "admin11", Set.of(Role.ADMIN, Role.MODERATOR, Role.CUSTOMER));
        }
    }

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
        return userRepository.findById(username);
    }

    private User addUser(String username, String password, Set<Role> roles) {
        boolean userAlreadyExisting = userRepository.findAll()
            .stream()
            .anyMatch(user -> user.getUsername().equals(username));
        if (userAlreadyExisting) {
            throw new UserAlreadyExistsException();
        }
        String hashedPassword = userPasswordHasher.hash(password);
        User user = new User(username, roles, hashedPassword);
        userRepository.save(user);
        return user;
    }
}
