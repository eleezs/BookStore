package com.example.bookstore.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String transactionId;

  private Double amount;

  private String paymentMethod; // e.g., "CARD", "BANK_TRANSFER", "CASH"

  @Enumerated(EnumType.STRING)
  private PaymentStatus status; // e.g., "SUCCESS", "FAILED", "PENDING"

  @Column(updatable = false) // Prevent updates to this field
  private LocalDateTime createdAt;

  @Column(nullable = false, length = 50)
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user; // Reference to the user making the payment

  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;



  public enum PaymentStatus {
    SUCCESS, FAILED, PENDING, CANCELLED
  }

  // public Transaction() {
  // }

  public Transaction(Double amount, String paymentMethod, PaymentStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, User user, Order order) {
    this.amount = amount;
    this.paymentMethod = paymentMethod;
    this.status = status;
    this.user = user;
    this.order = order;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters and Setters - TODO: remove getter and setters code
  // public Long getId() {
  //   return id;
  // }

  // public void setId(Long id) {
  //   this.id = id;
  // }

  // public Double getAmount() {
  //   return amount;
  // }

  // public void setAmount(Double amount) {
  //   this.amount = amount;
  // }

  // public String getPaymentMethod() {
  //   return paymentMethod;
  // }

  // public void setPaymentMethod(String paymentMethod) {
  //   this.paymentMethod = paymentMethod;
  // }

  // public PaymentStatus getStatus() {
  //   return status;
  // }

  // public void setStatus(PaymentStatus status) {
  //   this.status = status;
  // }

  // public LocalDateTime getCreatedAt() {
  //   return createdAt;
  // }

  // public void setCreatedAt(LocalDateTime createdAt) {
  //   this.createdAt = createdAt;
  // }

  // public LocalDateTime getUpdatedAt() {
  //   return updatedAt;
  // }

  // public void setUpdatedAt(LocalDateTime updatedAt) {
  //   this.updatedAt = updatedAt;
  // }

  // public User getUser() {
  //   return user;
  // }

  // public void setUser(User user) {
  //   this.user = user;
  // }

  // public Order getOrder() {
  //   return orderId;
  // }

  // public void setOrder(Order order) {
  //   this.orderId = order;
  // }

  // public String getTransactionId() {
  //   return transactionId;
  // }

  // public void setTransactionId(String transactionId) {
  //   this.transactionId = transactionId;
  // }

}
