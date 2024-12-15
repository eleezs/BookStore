// package com.example.bookstore;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
// class DemoApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}

// }

package com.example.bookstore;

import com.example.bookstore.controller.UserController;
import com.example.bookstore.dto.GetBooksDto;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BookstoreApplicationTests {

    @Autowired
    @Mock
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @InjectMocks
    private BookService bookService;
    private UserController userController;

    private Optional<Book> book1;
    private Optional<Book> book2;
    private Long id = (long) 1;

    @BeforeEach
    void setUp() {
        book1 = bookRepository.findById(id);
        book2 = bookRepository.findById(id + 1);
    }

    // Tests for Book Repository
    @Test
    void getAllBooks_whenRepositoryReturnsBooks_thenReturnBooks() {
        // Given
        when(bookRepository.findAll()).thenReturn(List.of(book1.get(), book2.get()));
        System.out.println(book1.get().toString() + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");

        // When
        List<Book> books = bookRepository.findAll();

        // Then
        assertEquals(2, books.size());
        assertTrue(books.contains(book1.get()));
        assertTrue(books.contains(book2.get()));
    }

    @Test
    void getBookById_whenRepositoryReturnsBook_thenReturnBook() {
        // Given
        when(bookRepository.findById(id)).thenReturn(Optional.of(book1.get()));

        // When
        Optional<Book> book = bookRepository.findById(id);

        // Then
        assertTrue(book.isPresent());
        assertEquals(book1.get(), book.get());
    }

    @Test
    void getBookById_whenRepositoryReturnsEmpty_thenReturnEmptyOptional() {
        // Given
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Book> book = bookRepository.findById(id);

        // Then
        assertFalse(book.isPresent());
    }

    // Tests for Book Service
    @Test
    void getAllBooks_whenServiceCallsRepository_thenReturnBooks() {
        // Given
        when(bookRepository.findAll()).thenReturn(List.of(book1.get(), book2.get()));

        // When
        Page<Book> books = bookService.getAllBooks(new GetBooksDto());
        List<Book> booksList = books.getContent();

        // Then
        assertEquals(2, booksList.size());
        assertTrue(booksList.contains(book1.get()));
        assertTrue(booksList.contains(book2.get()));
    }

    @Test
    void getBookById_whenServiceCallsRepository_thenReturnBook() {
        // Given
        when(bookRepository.findById(id)).thenReturn(Optional.of(book1.get()));

        // When
        Optional<Book> book = Optional.of(bookService.getBookById(id));

        // Then
        assertTrue(book.isPresent());
        assertEquals(book1, book.get());
    }

    @Test
    void getBookById_whenServiceCallsRepository_thenReturnEmptyOptional() {
        // Given
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<Book> book = Optional.of(bookService.getBookById(id));

        // Then
        assertFalse(book.isPresent());
    }

    // Unit test for retrieving a non-existent user
    
    @Test
    void getUserById_whenUserDoesNotExist_thenReturnNotFound() {
        // Given
        Long nonExistentUserId = (long) 9999;
        when(userRepository.findById(nonExistentUserId)).thenReturn(Optional.empty());
    
        // When
        ResponseEntity<User> response = userController.getUserById(nonExistentUserId);
    
        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
