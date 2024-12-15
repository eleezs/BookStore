package com.example.bookstore.controller;

import com.example.bookstore.dto.CreateBookDto;
import com.example.bookstore.dto.UpdateBookDto;
import com.example.bookstore.exception.DuplicateException;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.service.BookService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class BookControllerTest {

	private final BookService bookService = Mockito.mock(BookService.class);
	private final BookController bookController = new BookController(bookService);

    @Test
    void getBookById_shouldReturnBook() throws EntityNotFoundException {
         // Arrange
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId); // setting the ID for the book
        when(bookService.getBookById(bookId)).thenReturn(book);

         // Act
        ResponseEntity<Book> response = bookController.getBookById(bookId);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(book);

    }

    @Test
    void getBookById_shouldThrowEntityNotFoundException() {
        // Arrange
        Long bookId = 1L;
        when(bookService.getBookById(bookId)).thenThrow(new EntityNotFoundException("Book not found"));

        try {
            bookController.getBookById(bookId);
        }
        catch (EntityNotFoundException e) {
			assertThat(e.getMessage()).isEqualTo("Book not found");
        }

    }

    @Test
    void createBook_shouldCreateBook() throws DuplicateException {
        CreateBookDto createBookDto = new CreateBookDto();
        Book book = new Book();
        when(bookService.saveBook(createBookDto)).thenReturn(book);
        ResponseEntity<Book> response = bookController.createBook(createBookDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(book);
    }

	@Test
    void createBook_shouldThrowDuplicateException() {
        CreateBookDto createBookDto = new CreateBookDto();
        when(bookService.saveBook(createBookDto)).thenThrow(new DuplicateException("ISBN already exists"));
        try {
            bookController.createBook(createBookDto);
        }
        catch (DuplicateException e) {
            assertThat(e.getMessage()).isEqualTo("ISBN already exists");
        }

    }

    @Test
    void updateBook_shouldUpdateBook() throws EntityNotFoundException {
        Long bookId = 1L;
        UpdateBookDto updateBookDto = new UpdateBookDto();
        Book existingBook = new Book();
        when(bookService.updateBook(bookId, updateBookDto)).thenReturn(existingBook);
        ResponseEntity<Book> response = bookController.updateBook(bookId, updateBookDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(existingBook);


    }



    @Test
    void deleteBook_shouldDeleteBook() throws EntityNotFoundException {
        Long bookId = 1L;
        ResponseEntity<Void> response = bookController.deleteBook(bookId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
