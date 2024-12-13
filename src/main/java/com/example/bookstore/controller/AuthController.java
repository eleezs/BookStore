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

import com.example.bookstore.dto.SignUpDto;
import com.example.bookstore.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @CrossOrigin(origins = "*")
  @PostMapping("/signup")
  public ResponseEntity<AuthService.AuthResponse> signup(@Valid @RequestBody SignUpDto signUpDto) {
    AuthService.AuthResponse response = authService.signupUser(signUpDto);
    return ResponseEntity.ok(response);
  }
}
