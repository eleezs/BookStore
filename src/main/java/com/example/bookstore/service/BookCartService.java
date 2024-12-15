package com.example.bookstore.service;

import com.example.bookstore.dto.CartItemDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.BookCart;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.repository.BookCartRepository;
import com.example.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import com.example.bookstore.exception.EntityNotFoundException;

@Service
public class BookCartService {

  private final BookCartRepository bookCartRepository;
  private final BookRepository bookRepository;

  public BookCartService(BookCartRepository bookCartRepository, BookRepository bookRepository) {
    this.bookCartRepository = bookCartRepository;
    this.bookRepository = bookRepository;
  }

  public BookCart addToCart(Long userId, CartItemDto cartItemDto) {
    Long bookId = (long) cartItemDto.getBookId();
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Book not found"));

    BookCart cart = bookCartRepository.findByUserId(userId).orElse(new BookCart());
    cart.setUserId(userId);

    Optional<CartItem> existingItem = cart.getItems().stream()
        .filter(item -> item.getBook().getId().equals(book.getId()))
        .findFirst();

    if (existingItem.isPresent()) {
      // Update the quantity of the existing item
      CartItem cartItem = existingItem.get();
      cartItem.setQuantity(cartItem.getQuantity() + 1);
    } else {
      CartItem newItem = new CartItem();
      newItem.setBook(book);
      newItem.setQuantity(cartItemDto.getQuantity());
      cart.addCartItem(newItem);
    }

    return bookCartRepository.save(cart);
  }

  public BookCart getCart(Long userId) {
    return bookCartRepository.findByUserId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Cart not found for user: " + userId));
  }

  public void deleteCartItem(Long itemId, Long userId) {
    BookCart cart = bookCartRepository.findByUserId(userId)
        .orElseThrow(() -> new EntityNotFoundException("Cart not found for user: " + userId));

    // Find the book to be removed
    CartItem itemToRemove = cart.getItems().stream()
        .filter(item -> item.getId().equals(itemId))
        .findFirst()
        .orElseThrow(() -> new EntityNotFoundException("Cart item not found: " + itemId));

    // Calculate amount to subtract (item price * quantity)
    Double quantity = (double) itemToRemove.getQuantity();
    Double amountToSubtract = itemToRemove.getBook().getPrice() * quantity;
    // cart.setTotalPrice(cart.getTotalPrice() - bookToRemove.getPrice());

    // Update cart total
    cart.setTotalPrice(cart.getTotalPrice() - (amountToSubtract));

    // Remove the book from the cart
    cart.getItems().removeIf(item -> item.getId().equals(itemId));

    bookCartRepository.save(cart);
  }
}
