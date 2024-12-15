package com.example.bookstore.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "order_items")
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "id", nullable = false)
  private Book book;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private double price;

  @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "order_id", nullable = false) // Foreign key in the `order_items` table
  private Order order;
}
