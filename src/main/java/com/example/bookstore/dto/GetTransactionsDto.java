package com.example.bookstore.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.bookstore.model.Transaction.PaymentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetTransactionsDto {
    private String paymentMethod;
    private String userId;
    private String orderId;
    private PaymentStatus status;
    private LocalDateTime createdAt = LocalDateTime.now(); // Default to current time
    private int page = 0; // Default to 0
    private int size = 10; // Default to 10
}
