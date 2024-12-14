package com.example.bookstore.dto;

import jakarta.validation.constraints.*;
import com.example.bookstore.validation.ValidISBN;

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

  // Getters and Setters

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
