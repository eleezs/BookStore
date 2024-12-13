package com.example.bookstore.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bookstore.exception.JwtAuthenticationException;
import com.example.bookstore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String token = authHeader.substring(7); // Remove "Bearer " prefix

    try {
      DecodedJWT decodedJWT = jwtUtil.validateToken(token);
      String userEmail = jwtUtil.extractEmail(decodedJWT);
      String userType = jwtUtil.extractUserType(decodedJWT);

      UserDetails userDetails = User.withUsername(userEmail)
          .password("") // No password as JWT handles auth
          .authorities(userType)
          .build();

      // JwtAuthenticationToken authentication = new JwtAuthenticationToken(token,
      // userDetails.getAuthorities());
      var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (JWTVerificationException e) {
      // Throw the custom exception to be caught by the GlobalExceptionHandler
      throw new JwtAuthenticationException("Invalid token or session expired");
      // response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      // response.setContentType("application/json");
      // response.getWriter().write("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
      // return; 

    }

    filterChain.doFilter(request, response);
  }
}
