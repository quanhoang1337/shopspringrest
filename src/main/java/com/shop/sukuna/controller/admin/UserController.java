package com.shop.sukuna.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.User;
import com.shop.sukuna.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User postUser) {

        User user = this.userService.createUser(postUser);

        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable long id) {

        this.userService.deleteUser(id);

        return "delete user";
    }

    // fetch user by id
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        return this.userService.fetchUserById(id);
    }

    // fetch all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userService.fetchAllUsers();
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        User updatedUser = this.userService.updateUser(user);
        return updatedUser;
    }

}
