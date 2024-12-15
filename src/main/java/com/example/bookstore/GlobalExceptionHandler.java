package com.example.bookstore;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.bookstore.exception.*;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NoHandlerFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("status", 404);
    response.put("message", "There was no resource for this request");

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> invalidRequest(MethodArgumentNotValidException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    // response.put("status", 422);

    // Extract validation errors
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    response.put("errors", errors);

    return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(JwtAuthenticationException.class)
  public ResponseEntity<Map<String, Object>> handleJwtAuthenticationError(JwtAuthenticationException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    String message = ex.getMessage();
    System.out.println("JwtAuthenticationFilter: Authentication failed - " + ex.getMessage());
    // Customize the error message based on the content of the exception
    if (message.contains("expired")) {
      response.put("message", "Your session has expired, please log in again.");
    } else if (message.contains("invalid")) {
      response.put("message", "Invalid token. Please log in with a valid token.");
    } else {
      response.put("message", "Invalid token or session expired.");
    }

    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(DuplicateException.class)
  public ResponseEntity<Map<String, Object>> handleDuplicateISBN(DuplicateException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.CONFLICT); // 409 Conflict
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, Object>> EntityNotFoundException(EntityNotFoundException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Conflict
  }

  @ExceptionHandler(PaymentProcessingException.class)
  public ResponseEntity<Map<String, Object>> handlePaymentError(PaymentProcessingException ex) {
    Map<String, Object> response = new HashMap<>();
    response.put("success", false);
    response.put("message", ex.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
