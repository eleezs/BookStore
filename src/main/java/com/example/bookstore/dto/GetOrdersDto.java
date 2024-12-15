package com.example.bookstore.dto;

import java.time.LocalDate;

import com.example.bookstore.model.Order.StatusEnum;

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
public class GetOrdersDto {
    private String userId;
    private String bookId;
    private StatusEnum status;
    private LocalDate createdAt = LocalDate.now(); // Default to current date
    private int page = 0; // Default to 0
    private int size = 10; // Default to 10
}
