package com.example.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.bookstore.model.Transaction;
import com.example.bookstore.model.Transaction.PaymentMethod;
import com.example.bookstore.model.Transaction.PaymentStatus;
import com.example.bookstore.repository.TransactionRepository;
import com.example.bookstore.dto.GetTransactionsDto;
import com.example.bookstore.dto.TransactionDto;

import com.example.bookstore.exception.EntityNotFoundException;

import jakarta.persistence.criteria.Predicate;

import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {
  @Autowired
  private final TransactionRepository transactionRepository;

  public TransactionService(TransactionRepository transactionRepository) {
    this.transactionRepository = transactionRepository;
  }

	public Page<Transaction> getAllTransactions(GetTransactionsDto getTransactionDto) {
		Pageable pageable = PageRequest.of(getTransactionDto.getPage(), getTransactionDto.getSize());
		return transactionRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			String paymentMethod = getTransactionDto.getPaymentMethod();
			
			String userId = getTransactionDto.getUserId();
			String orderId = getTransactionDto.getOrderId();
			PaymentStatus status = getTransactionDto.getStatus();
			LocalDateTime createdAt = getTransactionDto.getCreatedAt();

			if (paymentMethod != null && !paymentMethod.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("paymentMethod")), "%" + paymentMethod.toLowerCase() + "%"));
			}
			if (userId != null && !userId.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("userId")), "%" + userId.toLowerCase() + "%"));
			}
			if (orderId != null && !orderId.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("orderId")), "%" + orderId.toLowerCase() + "%"));
			}
			if (status != null) {
				predicates.add(cb.equal(root.get("status"), status));
			}
			if (createdAt != null) {
				predicates.add(cb.equal(root.get("createdAt"), createdAt));
			}

			return cb.and(predicates.toArray(new Predicate[0]));

		}, pageable);
	}

	public Transaction getTransactionById(Long id) {
		return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
	}

	public Transaction updateTransactionStatus(Long id, String status) {
    Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Transaction not found"));
    transaction.setStatus(PaymentStatus.valueOf(status));
		return transactionRepository.save(transaction);
	}

  public Transaction processTransaction(TransactionDto transactionDto) {
    Transaction transaction = new Transaction();
    transaction.setAmount(transactionDto.getAmount());
    transaction.setPaymentMethod(PaymentMethod.valueOf(transactionDto.getPaymentMethod()));
    transaction.setCreatedAt(LocalDateTime.now());
    transaction.setStatus(PaymentStatus.valueOf(simulatePaymentProcessing()));
    transactionRepository.save(transaction);
    return transaction;
  }

  private String simulatePaymentProcessing() {
    // Simulate a random payment outcome
    return Math.random() > 0.2 ? PaymentStatus.SUCCESS.name() : PaymentStatus.FAILED.name(); // 80% success rate
  }
}
