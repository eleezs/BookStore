package com.example.bookstore.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class ISBNValidator implements ConstraintValidator<ValidISBN, String> {

    private static final String ISBN_PATTERN = "^(\\d{10}|\\d{13})$"; // 10 or 13 digits

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Allow null or empty since @NotBlank should handle required fields
        }
        return Pattern.matches(ISBN_PATTERN, value);
    }
}
