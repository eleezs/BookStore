package com.example.bookstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret_key}")
  private String JWT_SECRET_KEY;

  public DecodedJWT validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET_KEY.getBytes());
      JWTVerifier verifier = JWT.require(algorithm).build();
      return verifier.verify(token); // Decodes and verifies the token
    } catch (JWTVerificationException e) {
      throw e;
    }
  }

  public String extractEmail(DecodedJWT decodedJWT) {
    return decodedJWT.getSubject();
  }

  public String extractUserType(DecodedJWT decodedJWT) {
    return decodedJWT.getClaim("user_type").asString();
  }
}
