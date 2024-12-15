package com.example.bookstore.controller;

import com.example.bookstore.dto.GetOrdersDto;
import com.example.bookstore.dto.OrderRequestDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.service.OrderService;
import com.example.bookstore.service.TransactionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.Transaction.PaymentMethod;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final TransactionService transactionService;

    public OrderController(OrderService orderService, TransactionService transactionService) {
        this.orderService = orderService;
        this.transactionService =  transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrder(@RequestParam Long userId) {
        Order order = orderService.makeOrder(userId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Transaction> processTransaction(
            @PathVariable Long orderId,
            @RequestParam PaymentMethod paymentMethod) {
        Order order = orderService.getOrderById(orderId); // Method to fetch order by ID
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        System.out.println("order: " + order);
        Transaction transaction = transactionService.processTransaction(order, paymentMethod);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    return ResponseEntity.ok(order);
    }

    // @GetMapping("/")
    // public ResponseEntity<Map<String, Object>> getAllOrders(@Valid GetOrdersDto
    // getOrdersDto) {
    // Page<Order> orderPage = orderService.getAllOrders(getOrdersDto);

    // Map<String, Object> response = new HashMap<>();
    // response.put("orders", orderPage.getContent());
    // response.put("currentPage", orderPage.getNumber());
    // response.put("totalItems", orderPage.getTotalElements());
    // response.put("totalPages", orderPage.getTotalPages());

    // return ResponseEntity.ok(response);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody
    // OrderRequestDto orderRequestDto) {
    // Order order = orderService.updateOrder(id, orderRequestDto);
    // return ResponseEntity.ok(order);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    // orderService.deleteOrder(id);
    // return ResponseEntity.noContent().build();
    // }
}
