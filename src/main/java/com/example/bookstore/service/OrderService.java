package com.example.bookstore.service;

import com.example.bookstore.dto.GetOrdersDto;
import com.example.bookstore.dto.OrderRequestDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.Order.StatusEnum;
import com.example.bookstore.repository.OrderRepository;

import jakarta.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        order.setUser(orderRequestDto.getUser());
        order.setBooks(orderRequestDto.getBooks());
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        order.setStatus(orderRequestDto.getStatus());
        order.setOrderDate(orderRequestDto.getOrderDate());
        order.setDeliveryDate(orderRequestDto.getDeliveryDate());

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return order;
    }

	public Page<Order> getAllOrders(GetOrdersDto getOrdersDto) {
		int size = getOrdersDto.getSize();
		int offset = getOrdersDto.getPage() == 0 ? 1 : +getOrdersDto.getPage();
		int page = (offset - 1) * size;
		Pageable pageable = PageRequest.of(page, size);

		return orderRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			String userId = getOrdersDto.getUserId();
			String bookId = getOrdersDto.getBookId();
			StatusEnum status = getOrdersDto.getStatus();
			LocalDate createdAt = getOrdersDto.getCreatedAt();

			if (userId != null && !userId.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("userId")), userId));
			}
			if (bookId != null && !bookId.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("bookId")), bookId));
			}
			if (status != null) {
				predicates.add(cb.equal(root.get("status"), status));
			}
			if (createdAt != null) {
				predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("createdAt")), createdAt));
			}

			return cb.and(predicates.toArray(new Predicate[0]));

		}, pageable);
	}

    public Order updateOrder(Long id, OrderRequestDto orderRequestDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setUser(orderRequestDto.getUser());
        order.setBooks(orderRequestDto.getBooks());
        order.setTotalAmount(orderRequestDto.getTotalAmount());
        order.setStatus(orderRequestDto.getStatus());
        order.setOrderDate(orderRequestDto.getOrderDate());
        order.setDeliveryDate(orderRequestDto.getDeliveryDate());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}