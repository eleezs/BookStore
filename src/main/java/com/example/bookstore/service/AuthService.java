package com.example.bookstore.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import com.example.bookstore.model.User;
import com.example.bookstore.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.secret_key}")
    private String JWT_SECRET_KEY;

    @Value("${jwt.expiration_date:1800}")
    private long EXPIRATION_TIME;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String generateToken(User user, String userType) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("user_type", userType)
                .withClaim("id", user.getId().toHexString())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(JWT_SECRET_KEY.getBytes()));
    }

    public AuthResponse signupUser(String email, String firstName, String lastName, String password) {
        // Input validation
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Check if the email is already registered
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setEmail(email.trim());
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // Hash the password before saving
        String hashedPassword = passwordEncoder.encode(password);
        user.setPassword(hashedPassword);

        user = userRepository.save(user);

        String token = generateToken(user, "customer");
        String redisKey = "settings:user:" + user.getId().toHexString() + "_signup";
        redisTemplate.opsForValue().set(redisKey, token, EXPIRATION_TIME, TimeUnit.SECONDS);

        return new AuthResponse(mapUserToUserDTO(user), token);
    }

    private UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId().toHexString());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        // Don't set the password in DTO for security reasons
        return userDTO;
    }
}
