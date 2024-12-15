package com.example.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.bookstore.model.BookCart;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.Order.StatusEnum;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.Transaction.PaymentMethod;
import com.example.bookstore.model.Transaction.PaymentStatus;
import com.example.bookstore.repository.BookCartRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.repository.TransactionRepository;
import com.example.bookstore.dto.GetTransactionsDto;
import com.example.bookstore.dto.TransactionDto;

import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.exception.PaymentProcessingException;

import jakarta.persistence.criteria.Predicate;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

	private final TransactionRepository transactionRepository;
	// private final BookCartRepository bookCartRepository;
	private final OrderRepository orderRepository;

	public TransactionService(TransactionRepository transactionRepository, OrderRepository orderRepository) {
		this.transactionRepository = transactionRepository;
		this.orderRepository = orderRepository;
	}

	// public Page<Transaction> getAllTransactions(GetTransactionsDto
	// getTransactionDto) {
	// Pageable pageable = PageRequest.of(getTransactionDto.getPage() - 1,
	// getTransactionDto.getSize());
	// return transactionRepository.findAll((root, query, cb) -> {
	// List<Predicate> predicates = new ArrayList<>();
	// String paymentMethod = getTransactionDto.getPaymentMethod();

	// String userId = getTransactionDto.getUserId();
	// String orderId = getTransactionDto.getOrderId();
	// PaymentStatus status = getTransactionDto.getStatus();
	// LocalDateTime createdAt = getTransactionDto.getCreatedAt();

	// if (paymentMethod != null && !paymentMethod.isEmpty()) {
	// predicates.add(cb.like(cb.lower(root.get("paymentMethod")), "%" +
	// paymentMethod.toLowerCase() + "%"));
	// }
	// if (userId != null && !userId.isEmpty()) {
	// predicates.add(cb.like(cb.lower(root.get("userId")), "%" +
	// userId.toLowerCase() + "%"));
	// }
	// if (orderId != null && !orderId.isEmpty()) {
	// predicates.add(cb.like(cb.lower(root.get("orderId")), "%" +
	// orderId.toLowerCase() + "%"));
	// }
	// if (status != null) {
	// predicates.add(cb.equal(root.get("status"), status));
	// }
	// if (createdAt != null) {
	// predicates.add(cb.equal(root.get("createdAt"), createdAt));
	// }

	// return cb.and(predicates.toArray(new Predicate[0]));

	// }, pageable);
	// }

	// public Transaction getTransactionById(Long id) {
	// return transactionRepository.findById(id).orElseThrow(() -> new
	// EntityNotFoundException("Transaction not found"));
	// }

	// public Transaction updateTransactionStatus(Long id, String status) {
	// Transaction transaction = transactionRepository.findById(id)
	// 		.orElseThrow(() -> new EntityNotFoundException(
	// 				"Transaction not found"));transaction.setStatus(PaymentStatus.valueOf(status));return transactionRepository.save(transaction);
	// }

	public Transaction processTransaction(Order order, PaymentMethod paymentMethod) {
		Transaction transaction = new Transaction();
		transaction.setOrder(order);
		transaction.setAmount(order.getTotalPrice());
		transaction.setPaymentMethod(paymentMethod);

		try {
			// Simulated payment logic
			boolean paymentSuccessful = simulatePayment(paymentMethod, order.getTotalPrice());
			transaction.setStatus(paymentSuccessful ? PaymentStatus.valueOf("SUCCESS") : PaymentStatus.valueOf("FAILED"));
		} catch (Exception e) {
			transaction.setStatus(PaymentStatus.valueOf("FAILED"));
			throw new PaymentProcessingException("Payment failed: " + e.getMessage());
		}

		if(transaction.getStatus() == PaymentStatus.valueOf("SUCCESS")) order.setStatus(StatusEnum.valueOf("COMPLETED"));

		orderRepository.save(order);
		// Save the transaction log
		return transactionRepository.save(transaction);
	}

	private boolean simulatePayment(PaymentMethod paymentMethod, double amount) {
		// Simulate payment success or failure
		return Math.random() > 0.2; // 80% chance of success
	}
}
