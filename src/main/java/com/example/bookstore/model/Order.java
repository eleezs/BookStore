package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  // @JoinColumn(name = "id")
  private List<OrderItem> items = new ArrayList<>();

  @Column(nullable = false)
  private double totalPrice;

  @Enumerated(EnumType.STRING)
  private StatusEnum status;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column
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

  public enum StatusEnum {
    PENDING, COMPLETED, CANCELLED
  }
}
