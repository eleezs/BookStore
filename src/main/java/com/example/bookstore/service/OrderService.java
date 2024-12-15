package com.example.bookstore.service;

import com.example.bookstore.dto.GetOrdersDto;
import com.example.bookstore.dto.OrderRequestDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.BookCart;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order.StatusEnum;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.OrderRepository;
import jakarta.persistence.criteria.Predicate;
import com.example.bookstore.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookstore.repository.BookCartRepository;

@Service
public class OrderService {

    private final BookCartRepository bookCartRepository;
    private final OrderRepository orderRepository;

    public OrderService(BookCartRepository bookCartRepository, OrderRepository orderRepository) {
        this.bookCartRepository = bookCartRepository;
        this.orderRepository = orderRepository;
    }

    public Order makeOrder(Long userId) {
        BookCart cart = bookCartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found for user: " + userId));

        if (cart.getItems().isEmpty()) {
            throw new EntityNotFoundException("Cart is empty, cannot create order.");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(StatusEnum.valueOf("PENDING"));

        double totalPrice = 0.0;
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBook(cartItem.getBook());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getQuantity() * cartItem.getBook().getPrice());

            totalPrice += orderItem.getPrice();
            order.getItems().add(orderItem);
        }
        order.setTotalPrice(totalPrice);

        // Clear the cart after creating the order
        bookCartRepository.delete(cart);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));
    }
}
