CREATE TABLE books (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    authors VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL UNIQUE, -- Consider adding a UNIQUE constraint for ISBN
    title VARCHAR(255) NOT NULL,
    price DECIMAL(19,2) NOT NULL
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    city VARCHAR(60) NOT NULL,
    country_region VARCHAR(55) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE, -- Consider adding a UNIQUE constraint for email
    first_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    postal_code VARCHAR(18) NOT NULL,
    street_and_house_number VARCHAR(100) NOT NULL,
    last_name VARCHAR(50) NOT NULL
    user_type VARCHAR(50) NOT NULL
);