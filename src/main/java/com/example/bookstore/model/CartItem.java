package com.example.bookstore.model;

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
@Table(name = "cart_items")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  // @JoinColumn(name = "id", nullable = false)
  private Book book;

  // @ManyToOne
  // @JoinColumn(name = "id", nullable = false)
  // private BookCart cart;

  @Column(nullable = false)
  private int quantity;
}
