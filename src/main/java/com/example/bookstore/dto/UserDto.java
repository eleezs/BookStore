package com.example.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Only include non-null fields in JSON response
public class UserDto {
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private String phoneNumber;
    
    // Address information
    private AddressDto address;
    
    // Role information
    private String userType;
    
    // Timestamps
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AddressDto {
    private String streetAddress;
    private String streetAddress2;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private boolean isDefault;
}

