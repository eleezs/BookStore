package com.example.bookstore.model;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 254, unique = true)
    private String email;

    @Column(nullable = false, length = 254, unique = true)
    private String password;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(name = "phoneNumber", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "address", nullable = false, length = 18)
    private String address;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String userType;

    @Column(nullable = false, length = 50)
    private Date lastLoginAt;

    @Column(nullable = false, length = 50)
    private Date createdAt;

    @Column(nullable = false, length = 50)
    private Date updatedAt;
}
