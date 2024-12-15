package com.example.bookstore.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.bookstore.model.BookCart;
import com.example.bookstore.model.User;

public interface BookCartRepository extends JpaRepository<BookCart, Long> {
  Optional<BookCart> findByUserId(Long userId);
    
  // Optional<BookCart> findByUserAndIsActiveTrue(User user);
  
  // Optional<BookCart> findByUserIdAndIsActiveTrue(Long userId);
}
