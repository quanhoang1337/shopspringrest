package com.shop.sukuna.controller.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.sukuna.domain.User;
import com.shop.sukuna.service.UserService;
import com.shop.sukuna.util.error.IdInvalidException;

@RestController
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Create a user
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        User newUser = this.userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws IdInvalidException {

        this.userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Fetch user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) throws IdInvalidException {

        User user = this.userService.fetchUserById(id);

        if (user == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    // Fetch all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> user = this.userService.fetchAllUsers();

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // Update a user
    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {

        User updatedUser = this.userService.updateUser(user);

        return ResponseEntity.ok(updatedUser);
    }

}
