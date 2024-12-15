package com.example.bookstore.controller;

import com.example.bookstore.dto.CartItemDto;
import com.example.bookstore.model.BookCart;
import com.example.bookstore.service.BookCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class BookCartController {
  private final BookCartService bookCartService;

  @PostMapping("/add/{userId}")
  public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto) {
    BookCart cart = bookCartService.addToCart(userId, cartItemDto);
    return ResponseEntity.ok(cart);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getCart(@PathVariable Long userId) {
    BookCart cart = bookCartService.getCart(userId);
    return ResponseEntity.ok(cart);
  }

  @DeleteMapping("/{userId}/items/{itemId}")
  public ResponseEntity<?> deleteCartItem(@PathVariable Long userId, @PathVariable Long itemId) {
    bookCartService.deleteCartItem(itemId, userId);
    return ResponseEntity.ok("Item removed from cart");
  }
}
