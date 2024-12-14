package com.example.bookstore.dto;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.model.Order.StatusEnum;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDto {

    private User user;
    private List<Book> books;
    private Double totalAmount;
    private StatusEnum status;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
}
