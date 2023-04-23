package com.revs.demo.junit;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@GetMapping
	List<Book> getBooks() {
		return bookService.getAll();
	}
	
	@GetMapping(path = "/{bookId}")
	Book findById(@PathVariable int bookId) {
		return bookService.getById(bookId);
	}
	
	@PostMapping
	Book createBook(@RequestBody @Validated Book book) {
		return bookService.addBook(book);
	}
	
	@PutMapping
	Book updateBook(@RequestBody @Valid Book book) {
		return bookService.updateBook(book);
	}
	
	@DeleteMapping(path = "/{bookId}")
	void deleteBook(@PathVariable int bookId) {
		bookService.removeBook(bookId);
	}
}
