package com.example.bookstore.repository;

import com.example.bookstore.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        // Basic query methods
        Optional<User> findByEmail(String email);
        boolean existsByEmail(String email);
    
}
