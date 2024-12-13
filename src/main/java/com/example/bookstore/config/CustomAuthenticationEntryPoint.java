package com.example.bookstore.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("success", false);

    String message = authException.getMessage();

    if (message.contains("expired")) {
      responseBody.put("message", "Your session has expired, please log in again.");
    } else if (message.contains("invalid")) {
      responseBody.put("message", "Invalid token. Please log in with a valid token.");
    } else {
      responseBody.put("message", "Unauthorized access.");
    }

    responseBody.put("status", 401);

    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getWriter().write(objectMapper.writeValueAsString(responseBody));
  }
}
