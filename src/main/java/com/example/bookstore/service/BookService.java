package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import com.example.bookstore.dto.CreateBookDto;
import com.example.bookstore.dto.GetBooksDto;
import com.example.bookstore.dto.UpdateBookDto;

import com.example.bookstore.exception.DuplicateException;
import com.example.bookstore.exception.EntityNotFoundException;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	public Page<Book> getAllBooks(GetBooksDto getBooksDto) {
		int size = getBooksDto.getSize();
		int offset = getBooksDto.getPage() == 0 ? 1 : getBooksDto.getPage();
		int page = (offset - 1) * size;
		System.out.println("here " + page + "we " + size + "here " + offset);
		Pageable pageable = PageRequest.of(page, size);

		return bookRepository.findAll((root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			String author = getBooksDto.getAuthor();
			String title = getBooksDto.getTitle();
			String isbn = getBooksDto.getIsbn();
			Double minPrice = getBooksDto.getMinPrice();
			Double maxPrice = getBooksDto.getMaxPrice();
			LocalDate createdAt = getBooksDto.getCreatedAt();
			Integer min_quantity = getBooksDto.getMinQuantity();
			Integer max_quantity = getBooksDto.getMaxQuantity();

			if (author != null && !author.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%"));
			}
			if (title != null && !title.isEmpty()) {
				predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
			}
			if (isbn != null && !isbn.isEmpty()) {
				predicates.add(cb.equal(root.get("isbn"), isbn));
			}
			if (minPrice != null) {
				predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
			}
			if (maxPrice != null) {
				predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
			}
			if (createdAt != null) {
				predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("createdAt")), createdAt));
			}
			if (min_quantity != null) {
				predicates.add(cb.equal(root.get("quantity"), min_quantity));
			}
			if (max_quantity != null) {
				predicates.add(cb.equal(root.get("quantity"), max_quantity));
			}
			if (createdAt != null) {
				predicates.add(cb.equal(cb.function("DATE", LocalDate.class, root.get("createdAt")), createdAt));
			}

			return cb.and(predicates.toArray(new Predicate[0]));

		}, pageable);
	}

	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found"));
	}

	public Book saveBook(CreateBookDto book) {
		if (bookRepository.existsByIsbn(book.getIsbn())) {
			throw new DuplicateException("ISBN already in use");
		}

		Book newBook = new Book();
		newBook.setTitle(book.getTitle());
		newBook.setAuthor(book.getAuthor());
		newBook.setPrice(book.getPrice());
		newBook.setIsbn(book.getIsbn());
		newBook.setQuantity(book.getQuantity());

		return bookRepository.save(newBook);
	}

	public void deleteBook(Long id) {
		bookRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Book not found"));
		bookRepository.deleteById(id);
	}

	public Book updateBook(Long id, UpdateBookDto book) {
		Book existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Book not found"));

		String isbn = book.getIsbn();
		if (isbn != null && !isbn.isEmpty() && bookRepository.existsByIsbn(isbn)) {
			throw new DuplicateException("ISBN already in use");
		}

		book.getTitle().ifPresent(existingBook::setTitle);
		book.getAuthor().ifPresent(existingBook::setAuthor);
		book.getPrice().ifPresent(existingBook::setPrice);
		book.getQuantity().ifPresent(existingBook::setQuantity);

		
		if (isbn != null && !isbn.isEmpty())
			existingBook.setIsbn(isbn);

		return bookRepository.save(existingBook);
	}
}
