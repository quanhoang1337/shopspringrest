package com.shop.sukuna.controller.admin;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import com.shop.sukuna.domain.response.PaginationResponse;
import com.shop.sukuna.domain.response.ResCreateUserDTO;
import com.shop.sukuna.domain.response.ResUpdateUserDTO;
import com.shop.sukuna.domain.response.ResUserDTO;
import com.shop.sukuna.service.UserService;
import com.shop.sukuna.util.annotation.ApiMessage;
import com.shop.sukuna.util.error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

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
    @ApiMessage("Create a user")
    public ResponseEntity<ResCreateUserDTO> createUser(@Valid @RequestBody User user)
            throws IdInvalidException {

        boolean isEmailExist = this.userService.isEmailExist(user.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException("Email " + user.getEmail() + " đã tồn tại, vui lòng sử dụng email khác");
        }

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        User newUser = this.userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.toResCreateUserDTO(newUser));

    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id)
            throws IdInvalidException {

        User user = this.userService.fetchUserById(id);
        if (user == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }

        this.userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // Fetch user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable long id)
            throws IdInvalidException {

        User user = this.userService.fetchUserById(id);

        if (user == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.toResUserDTO(user));

    }

    // Fetch all users
    @GetMapping("/users")
    public ResponseEntity<PaginationResponse> getAllUsers(
            @Filter Specification<User> spec,
            Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.fetchAllUsers(spec, pageable));
    }

    // Update a user
    @PutMapping("/users")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user)
            throws IdInvalidException {

        User updatedUser = this.userService.updateUser(user);

        if (updatedUser == null) {
            throw new IdInvalidException("User với id = " + user.getId() + " không tồn tại");
        }

        return ResponseEntity.ok(this.userService.toResUpdateUserDTO(updatedUser));
    }

}
