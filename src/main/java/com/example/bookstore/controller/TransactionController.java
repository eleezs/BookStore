package com.example.bookstore.controller;

import com.example.bookstore.dto.*;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.Transaction.PaymentStatus;
import com.example.bookstore.service.TransactionService;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // @GetMapping
    // public ResponseEntity<Map<String, Object>> getAllTransactions(
    //         @Valid @RequestParam GetTransactionsDto getTransactionsDto) {
    //     Page<Transaction> transactionPage = transactionService.getAllTransactions(getTransactionsDto);

    //     Map<String, Object> response = new HashMap<>();
    //     response.put("transactions", transactionPage.getContent());
    //     response.put("currentPage", transactionPage.getNumber());
    //     response.put("totalItems", transactionPage.getTotalElements());
    //     response.put("totalPages", transactionPage.getTotalPages());

    //     return ResponseEntity.ok(response);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
    //     Transaction transaction = transactionService.getTransactionById(id);
    //     return ResponseEntity.ok(transaction);
    // }

    // @PostMapping
    // public ResponseEntity<Transaction> createTransaction(@Validated @RequestBody TransactionDto transactionDto) {
    //     Transaction transaction = transactionService.processTransaction(transactionDto);
    //     return ResponseEntity.ok(transaction);
    // }

    // @PostMapping("/order/{cartId}")
    // public ResponseEntity<Order> createOrder(@PathVariable Long cartId) {
    //     Order order = transactionService.createOrder(cartId);
    //     return ResponseEntity.ok(order);
    // }

    // @PostMapping("/order/{orderId}/complete")
    // public ResponseEntity<Order> completeOrder(
    //         @PathVariable Long orderId,
    //         @RequestParam boolean paymentSuccess) {
    //     Order order = transactionService.completeOrder(orderId, paymentSuccess);
    //     return ResponseEntity.ok(order);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> cancelTransaction(@PathVariable Long id) {
    //     transactionService.updateTransactionStatus(id, PaymentStatus.CANCELLED.name());
    //     return ResponseEntity.noContent().build();
    // }
}
