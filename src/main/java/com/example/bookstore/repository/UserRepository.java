package com.example.bookstore.repository;

import com.example.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be added here if needed
}
