package com.example.bookstore.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.dto.*;
import com.example.bookstore.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @PostMapping("/signup")
  public ResponseEntity<AuthService.AuthResponse> signup(@Valid @RequestBody SignUpDto signUpDto) {
    AuthService.AuthResponse response = authService.signupUser(signUpDto);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthService.AuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
    AuthService.AuthResponse response = authService.loginUser(loginDto);
    return ResponseEntity.ok(response);
  }



}
