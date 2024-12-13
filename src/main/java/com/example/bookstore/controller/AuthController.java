package com.example.bookstore.controller;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.model.User;
import com.example.bookstore.dto.SignUpDTO;
import com.example.bookstore.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @CrossOrigin(origins = "*")
  @PostMapping("/signup")
  public ResponseEntity<AuthResponse> signup(@Valid @RequestBody signUpDto signUpDto) {
    AuthResponse response = authService.signupUser(signupDTO);
    return ResponseEntity.ok(response);
}
