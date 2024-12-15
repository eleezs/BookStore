package com.example.bookstore.dto;

import jakarta.validation.constraints.*;
import com.example.bookstore.validation.ValidISBN;

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
public class CreateBookDto {

  @NotBlank(message = "Title is required")
  @Size(min = 2, max = 100, message = "Title must have between 2 and 100 characters")
  private String title;

  @NotBlank(message = "Author name is required")
  @Size(min = 2, max = 50, message = "Author name must have between 2 and 50 characters")
  private String author;

  @ValidISBN(message = "ISBN must be a valid 13-digit ISBN-10 or 10-digit ISBN-13")
  private String isbn;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.01", message = "Price must be greater than 0")
  @Digits(integer = 10, fraction = 2, message = "Price must be a valid monetary amount. i.e 2dp")
  private Double price;

  @NotNull(message = "Quantity is required")
  @Min(value = 1, message = "Quantity must be at least 1")
  private Integer quantity;
}
