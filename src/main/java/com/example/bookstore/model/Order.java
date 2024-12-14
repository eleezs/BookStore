package com.example.bookstore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user; // Reference to the user who placed the order

  // @OneToOne
  // @JoinTable(name = "book_order_books", joinColumns = @JoinColumn(name = "book_order_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
  // private List<Book> books; // List of books in the order


  // @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  // @JsonBackReference
  // private Book books; // List of books in the order

  // @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  // @JsonBackReference
  // private Order order;
  

  @OneToMany
  private List<Book> books = new ArrayList<>();
  
  private Double totalAmount; // Total price of the order

  @Enumerated(EnumType.STRING)
  private StatusEnum status; // Order status (PENDING, COMPLETED, CANCELLED)

  private LocalDateTime orderDate; // Date and time when the order was placed

  private LocalDateTime deliveryDate; // Estimated or actual delivery date

  // private Optional<String> shippingAddress = Optional.empty(); // Shipping details for the order

  @Column(updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
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
    PENDING,
    TRANSIT,
    COMPLETED,
    CANCELLED
  }
}
