package com.example.bookstore.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@Data
public class CartItemDto {
    private int bookId;
    private int quantity;
}
