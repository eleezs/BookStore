package com.example.bookstore;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

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
}
