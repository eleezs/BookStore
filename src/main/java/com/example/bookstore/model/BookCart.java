package com.example.bookstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book_cart")
public class BookCart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long userId; // User identifier

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  // @JoinColumn(name = "cart_id") // Maps CartItem to this BookCart
  private List<CartItem> items = new ArrayList<>();

  private double totalPrice;

  // Add an item to the cart
  public void addCartItem(CartItem cartItem) {
    items.add(cartItem);
    totalPrice += cartItem.getBook().getPrice() * cartItem.getQuantity();
  }

  // Remove an item from the cart
  public void removeCartItem(CartItem cartItem) {
    items.remove(cartItem);
    totalPrice -= cartItem.getBook().getPrice() * cartItem.getQuantity();
  }

  // Clear the cart
  public void clearCart() {
    items.clear();
    totalPrice = 0.0;
  }
}
