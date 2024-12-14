# Book Store Application with Spring Boot and Redis

This repository contains a sample Book Store application built using Spring Boot and Redis. The application provides RESTful APIs for managing books, authors, categories, and orders. It also includes features like user authentication, role-based access control, request validation, caching, and Redis integration.

## Table of Contents

- [Book Store Application with Spring Boot and Redis](#book-store-application-with-spring-boot-and-redis)
  - [Table of Contents](#table-of-contents)
  - [Project Structure](#project-structure)
  - [Getting Started](#getting-started)
  - [API Documentation](#api-documentation)
  - [Features](#features)
    - [User Authentication](#user-authentication)
    - [Role-Based Access Control](#role-based-access-control)
    - [Request Validation](#request-validation)
    - [Caching](#caching)
    - [Redis Integration](#redis-integration)
  - [Testing](#testing)
  - [Deployment](#deployment)

## Project Structure

The project structure is as follows:

```
bookstore/
├── src/main/java/com/example/bookstore/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── exception/
│   ├── repository/
│   ├── security/
│   ├── service/
│   └── BookstoreApplication.java
├── src/main/resources/
│   ├── application.properties
│   ├── static/
│   └── templates/
├── pom.xml
└── README.md
```

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/bookstore.git
   ```

2. Change into the project directory:

   ```bash
   cd bookstore
   ```

3. Install the required dependencies by running:

   ```bash
   mvn clean install
   ```

4. Configure the database connection in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/bookstore?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

5. Run the application:

   ```bash
   mvn spring-boot:run
   ```

## API Documentation

The API documentation is available at `http://localhost:8080/swagger-ui.html`.

## Features

### User Authentication

The application uses Spring Security for user authentication. It supports both basic authentication and JWT (JSON Web Tokens) authentication.

To use basic authentication, send an HTTP request with the `Authorization` header containing the base64 encoded username and password.

To use JWT authentication, send an HTTP request with the `Authorization` header containing the JWT token.

### Role-Based Access Control

The application implements role-based access control using Spring Security. Users can have different roles (e.g., ADMIN, USER), and each role has access to specific resources.

To assign roles to users, you can modify the `User` entity and add a `Set<Role>` field. Then, create a `Role` entity and a `RoleRepository` to manage roles in the database.

### Request Validation

The application uses Spring Validation to validate incoming request payloads. You can add validation annotations (e.g., `@NotNull`, `@Size`, `@Pattern`) to the DTO (Data Transfer Object) classes.

### Caching

The application uses Spring Cache to cache frequently accessed data. You can annotate methods with `@Cacheable` to cache the results and `@CacheEvict` to invalidate the cache when data is updated.

### Redis Integration

The application integrates Redis for caching and session management. To enable Redis caching, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

Then, configure Redis in `src/main/resources/application.properties`:

```properties
spring.redis.host=localhost
spring.redis.port=6379
```

To enable Redis session management, add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

Then, configure Redis session management in `src/main/resources/application.properties`:

```properties
spring.session.store-type=redis
spring.session.redis.flush-mode=on_save
spring.session.redis.timeout=60s
```

## Testing

The application includes unit tests using JUnit and Mockito. You can run the tests by executing:

```bash
mvn test
```

## Deployment

To deploy the application, you can build a JAR file using Maven and then run it using the Java command.

First, build the JAR file:

```bash
mvn clean package
```

Then, run the JAR file:

```bash
java -jar target/bookstore-0.0.1-SNAPSHOT.jar
```

You can also deploy the application to a containerized environment like Docker or Kubernetes.

That's it! You now have a fully functional Book Store application with user authentication, role-based access control, request validation, caching, and Redis integration.