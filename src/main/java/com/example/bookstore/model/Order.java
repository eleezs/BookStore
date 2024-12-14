package com.example.bookstore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`order`")
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
  
  @OneToOne(fetch = FetchType.LAZY)
  @JoinTable(
      name = "order_books",
      joinColumns = @JoinColumn(name = "order_id"),
      inverseJoinColumns = @JoinColumn(name = "book_id")
  )
  private List<Book> books = new ArrayList<>();

  @OneToOne
  private Transaction transaction;
  
  private Double totalAmount; // Total price of the order

  @Enumerated(EnumType.STRING)
  private statusEnum status; // Order status (PENDING, COMPLETED, CANCELLED)

  private LocalDateTime orderDate; // Date and time when the order was placed

  private LocalDateTime deliveryDate; // Estimated or actual delivery date

  // private Optional<String> shippingAddress = Optional.empty(); // Shipping details for the order

  @Column(updatable = false)
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
  
  public enum statusEnum {
    PENDING,
    TRANSIT,
    COMPLETED,
    CANCELLED
  }

  public Order() {
  }

  public Order(User user, List<Book> books, Double totalAmount, statusEnum status, LocalDateTime orderDate, LocalDateTime deliveryDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.user = user;
    this.books = books;
    this.totalAmount = totalAmount;
    this.status = status;
    this.orderDate = orderDate;
    this.deliveryDate = deliveryDate;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    // this.shippingAddress = Optional.ofNullable(shippingAddress);
  }

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public statusEnum getStatus() {
    return status;
  }

  public void setStatus(statusEnum status) {
    this.status = status;
  }

  public LocalDateTime getOrderDate() {
     return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
     this.orderDate = orderDate;
  }

  public LocalDateTime getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(LocalDateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  // public Optional<String> getShippingAddress() {
  //   return shippingAddress;
  // }

  // public void setShippingAddress(String shippingAddress) {
  //   this.shippingAddress = Optional.ofNullable(shippingAddress);
  // }
}
