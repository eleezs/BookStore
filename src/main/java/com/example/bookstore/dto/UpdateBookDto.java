package com.example.bookstore.dto;

import jakarta.validation.constraints.*;
import com.example.bookstore.validation.ValidISBN;
import java.util.Optional;

public class UpdateBookDto {
  @NotNull
  private Optional<String> title = Optional.empty();

  @NotNull
  private Optional<String> author = Optional.empty();

  @NotNull
  private Optional<Double> price = Optional.empty();

  @ValidISBN
  private String isbn;

  public Optional<String> getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = Optional.ofNullable(title);
  }

  public Optional<String> getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = Optional.ofNullable(author);
  }

  public Optional<Double> getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = Optional.ofNullable(price);
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }
}
