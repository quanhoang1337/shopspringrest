package com.shop.sukuna.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.sukuna.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
}
