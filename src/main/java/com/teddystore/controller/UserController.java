package com.teddystore.controller;

import com.teddystore.model.User;
import com.teddystore.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("{username}")
    public Optional<User> getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PutMapping("{id}")
    public void updateUserDetails(@RequestBody User user, @PathVariable Long id) {
        userService.updateUserDetails(id, user);
    }

    @DeleteMapping("{id}")
    void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @DeleteMapping
    void deleteUsers() {
        userService.deleteUsers();
    }
}
