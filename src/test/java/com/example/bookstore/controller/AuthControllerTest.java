package com.example.bookstore.controller;

import com.example.bookstore.dto.LoginDto;
import com.example.bookstore.dto.SignUpDto;
import com.example.bookstore.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @Autowired

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    void signup_shouldReturnOk() {
        SignUpDto signUpDto = new SignUpDto("John", "Doe", "john.doe@example.com", "Password123!");
        AuthService.AuthResponse expectedAuthResponse = authService.signupUser(signUpDto); // Create an expected response
        when(authService.signupUser(signUpDto)).thenReturn(expectedAuthResponse); // Mock the service call

        ResponseEntity<AuthService.AuthResponse> response = authController.signup(signUpDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedAuthResponse, response.getBody());  // Check against the expected response
    }


    @Test
    void login_shouldReturnOk() {
        LoginDto loginDto = new LoginDto("john.doe@example.com", "Password123!");
        AuthService.AuthResponse authResponse = authService.loginUser(loginDto);

        when(authService.loginUser(loginDto)).thenReturn(authResponse);


        ResponseEntity<AuthService.AuthResponse> response = authController.login(loginDto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
    }
}
