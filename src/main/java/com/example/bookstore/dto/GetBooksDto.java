package com.example.bookstore.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetBooksDto {
    private String author;
    private String title;
    private String isbn;
    private Double minPrice;
    private Double maxPrice;
    private LocalDateTime createdAt = LocalDateTime.now(); // Default to current time
    private int page = 0; // Default to 0
    private int size = 10; // Default to 10
}
