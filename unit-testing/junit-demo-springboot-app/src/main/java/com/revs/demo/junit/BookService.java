package com.revs.demo.junit;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.revs.demo.junit.exception.BadRequestException;

@Service
public class BookService {

//	@Autowired
	private IBookRepository bookRepo;
	
	@Autowired
	public BookService(IBookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}
	
	public List<Book> getAll() {
		return bookRepo.findAll();
	}
	
	Book getById(int bookId) {
		
		if(bookId == 0) {
			throw new BadRequestException("Book Id can't be Zero!");
		}
		
		Optional<Book> book = bookRepo.findById(bookId);
		
		if( book.isPresent()) {
			return book.get();
		} else {
			throw new BadRequestException("No Data");
		}
		
		
	}
	
	Book addBook(Book book) {
		String bookName = book.getName();
		
		boolean exists = bookRepo.findBookByName(bookName);
		
		if(exists) {
			throw new BadRequestException("Book " + bookName + " is already exists!");
		}
		
		return bookRepo.save(book);
	}
	
	Book updateBook(Book book) {
		Optional<Book> optionalBook;
		if (book == null || book.getId() == 0) {
			throw new BadRequestException("Book id should not be Zero");
		}
		
		optionalBook = bookRepo.findById(book.getId());
		
		if (!optionalBook.isPresent()) {
			throw new BadRequestException("Record nod found for ID " + book.getId());
		}
		
		Book bookExisting = optionalBook.get();
		
		bookExisting.setName(book.getName());
		bookExisting.setSummery(book.getSummery());
		bookExisting.setRating(book.getRating());
		
		return bookRepo.save(bookExisting);
	}
	
	void removeBook(@PathVariable int bookId) {
		Optional<Book> optionalBook;
		if (bookId == 0) {
			throw new RuntimeException("Book id should not be Zero");
		}
		
		optionalBook = bookRepo.findById(bookId);
		
		if(optionalBook.isPresent()) {
			bookRepo.delete(optionalBook.get());
		} else {
			throw new BadRequestException("No Book present with ID " + bookId);
		}
	}
	
}
