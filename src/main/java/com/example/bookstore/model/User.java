package com.example.bookstore.model;

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

    @Column(nullable = false, length = 60)
    private String city;

    @Column(name = "country_region", nullable = false, length = 55)
    private String countryRegion;

    @Column(nullable = false, length = 254, unique = true)
    private String email;

    @Column(nullable = false, length = 254, unique = true)
    private String password;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "postal_code", nullable = false, length = 18)
    private String postalCode;

    @Column(name = "street_and_house_number", nullable = false, length = 100)
    private String streetAndHouseNumber;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String userType;
}
