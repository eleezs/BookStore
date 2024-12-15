package com.example.bookstore.dto;

import jakarta.validation.constraints.*;
import com.example.bookstore.validation.ValidISBN;
import java.util.Optional;

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
public class UpdateBookDto {
  @NotNull
  private Optional<String> title = Optional.empty();

  @NotNull
  private Optional<String> author = Optional.empty();

  @NotNull
  private Optional<Double> price = Optional.empty();

  @ValidISBN
  private String isbn;

  @NotNull
  private Optional<Integer> quantity = Optional.empty();
}
