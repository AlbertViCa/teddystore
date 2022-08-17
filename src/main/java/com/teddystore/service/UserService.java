package com.teddystore.service;

import com.teddystore.model.User;

import java.util.Optional;

public interface UserService {

    User registerUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getByUsername(String username);

    Iterable<User> getUsers();

    User updateUserDetails(Long id, User user);

    void deleteUserById(Long id);

    void deleteUsers();
}
