package com.example.bookstore.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

// import com.example.bookstore.dto.LoginDTO;
import com.example.bookstore.dto.*;
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
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Value("${jwt.secret_key}")
	private String JWT_SECRET_KEY;

	@Value("${jwt.expiration_date: 1800}")
	private long EXPIRATION_TIME;

	private String generateToken(User user, String userType) {
		return JWT.create()
				.withSubject(user.getEmail())
				.withClaim("user_type", userType)
				.withClaim("id", user.getId())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 1000))
				.sign(Algorithm.HMAC512(JWT_SECRET_KEY.getBytes()));
	}

	public AuthResponse signupUser(SignUpDto signupDTO) {
		if (userRepository.existsByEmail(signupDTO.getEmail())) {
			throw new IllegalArgumentException("Email already in use");
		}

		User user = new User();
		user.setEmail(signupDTO.getEmail().trim());
		user.setFirstName(signupDTO.getFirstName());
		user.setLastName(signupDTO.getLastName());
		user.setUserType("user");
		user.setLastLoginAt(LocalDateTime.now());
		user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

		user = userRepository.save(user);

		String token = generateToken(user, "customer");
		String redisKey = "settings:user:" + user.getId() + "_token";
		redisTemplate.opsForValue().set(redisKey, token, EXPIRATION_TIME, TimeUnit.SECONDS);

		return new AuthResponse(mapUserToUserDTO(user), token);
	}

	public AuthResponse loginUser(LoginDto loginDto) {
		String email = loginDto.getEmail().trim();
		String password = loginDto.getPassword();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("Invalid email or password");
		}

		user.setLastLoginAt(LocalDateTime.now());
		user = userRepository.save(user);

		String token = generateToken(user, "customer");
		String redisKey = "settings:user:" + user.getId() + "_token";
		redisTemplate.opsForValue().set(redisKey, token, EXPIRATION_TIME, TimeUnit.SECONDS);

		return new AuthResponse(mapUserToUserDTO(user), token);
	};

	private UserDto mapUserToUserDTO(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		return userDto;
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
