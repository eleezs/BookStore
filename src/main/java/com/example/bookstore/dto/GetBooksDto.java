package com.example.bookstore.dto;

import java.time.LocalDate;

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
    private LocalDate createdAt = LocalDate.now(); // Default to current date
    private Integer minQuantity;
    private Integer maxQuantity;
    private int page = 0; // Default to 0
    private int size = 10; // Default to 10
}
