package com.example.bookstore.model;

import java.time.LocalDateTime;

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
@Table(name = "transactions")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // private String transactionId;

  private Double amount;

  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod; // e.g., "CARD", "BANK_TRANSFER", "CASH"

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

  // @OneToOne
  // @JoinColumn(name = "user_id")
  // private User user; // Reference to the user making the payment

  @OneToOne
  // @JoinColumn(name =   "id")
  private Order order;

  public enum PaymentStatus {
    SUCCESS, FAILED, PENDING, CANCELLED
  }

  public enum PaymentMethod {
    CARD, BANK_TRANSFER, REGISTERED, CASH, USSD
  }
}
