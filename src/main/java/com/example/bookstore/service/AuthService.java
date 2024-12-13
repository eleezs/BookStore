package com.example.bookstore.service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

// import com.example.bookstore.dto.LoginDTO;
import com.example.bookstore.dto.SignUpDto;
import com.example.bookstore.dto.UserDto;
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

    public AuthResponse signupUser(SignUpDto signupDTO) {
        // Check if email already exists
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Create new user
        User user = new User();
        user.setEmail(signupDTO.getEmail().trim());
        user.setFirstName(signupDTO.getFirstName());
        user.setLastName(signupDTO.getLastName());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        userRepository.save(user);

        // Generate JWT token
        String token = generateToken(user, "customer");

        // Store token in Redis
        String redisKey = "settings:user:" + user.getId() + "_token";
        redisTemplate.opsForValue().set(redisKey, token, EXPIRATION_TIME, TimeUnit.SECONDS);

        UserDto userDto = mapUserToUserDTO(user);
        return new AuthResponse(userDto, token);
    }

    // public AuthResponse loginUser(LoginDTO loginDTO) {
    // User user = userRepository.findByEmail(loginDTO.getEmail())
    // .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

    // if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
    // throw new BadCredentialsException("Invalid credentials");
    // }

    // String token = generateToken(user, "customer");

    // // Store token in Redis
    // String redisKey = "settings:user:" + user.getId().toHexString() + "_login";
    // redisTemplate.opsForValue().set(redisKey, token, EXPIRATION_TIME,
    // TimeUnit.SECONDS);

    // return new AuthResponse(mapUserToUserDto(user), token);
    // }

    private String generateToken(User user, String userType) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("user_type", userType)
                .withClaim("id", user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000))
                .sign(Algorithm.HMAC512(JWT_SECRET_KEY.getBytes()));
    }

    private UserDto mapUserToUserDTO(User user) {
        UserDto UserDto = new UserDto();
        UserDto.setId(user.getId());
        UserDto.setEmail(user.getEmail());
        UserDto.setFirstName(user.getFirstName());
        UserDto.setLastName(user.getLastName());
        return UserDto;
    }



    public static class AuthResponse {
        private UserDto user;
        private String token;
      
      
        public AuthResponse(UserDto user, String token) {
          this.user = user;
          this.token = token;
        }
      
        public UserDto getUser() {
          return user;
        }
      
        public void setUser(UserDto user) {
          this.user = user;
        }
      
        public String getToken() {
          return token;
        }
      
        public void setToken(String token) {
          this.token = token;
        }
      }
}
