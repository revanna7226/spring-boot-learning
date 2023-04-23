/**
 * 
 */
package com.revs.demo.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;

import com.revs.demo.junit.exception.BadRequestException;

/**
 * @author revsg
 *
 */
@ExtendWith(MockitoExtension.class)
@Description(value = "Youtube URL: https://youtu.be/Geq60OVyBPg")
class BookServiceTest {
	@Mock
	private IBookRepository bookRepo;
	// private AutoCloseable closable;
	private BookService underTest;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
//		closable = MockitoAnnotations.openMocks(this);
		underTest = new BookService(bookRepo);
	}

	/**
	 * @throws java.lang.Exception
	 */
//	@AfterEach
//	void tearDown() throws Exception {
//		closable.close();
//	}

	/**
	 * Test method for {@link com.revs.demo.junit.BookService#getAll()}.
	 */
	@Test
	void testGetAll() {
		// when
		underTest.getAll();
		// then
		verify(bookRepo).findAll();
	}

//	/**
//	 * Test method for {@link com.revs.demo.junit.BookService#getById(int)}.
//	 */
//	@Test
//	void testGetById() {
//		fail("Not yet implemented");
//	}
//
	/**
	 * Test method for
	 * {@link com.revs.demo.junit.BookService#addBook(com.revs.demo.junit.Book)}.
	 */
	@Test
	void testAddBook() {
		// given
		Book book = new Book(1, "Hero", "Heroic Success", 30);

		// when
		underTest.addBook(book);

		ArgumentCaptor<Book> argumenetCaptor = ArgumentCaptor.forClass(Book.class);

		verify(bookRepo).save(argumenetCaptor.capture());

		Book capturedBook = argumenetCaptor.getValue();
		// then
		assertThat(capturedBook).isEqualTo(book);
	}

	@Test
	void testThrowErrorOnAddingDuplicateBook() {
		// given
		Book book = new Book(1, "Hero", "Heroic Success", 30);

		// when
		when(bookRepo.findBookByName(book.getName())).thenReturn(true);

		assertThatThrownBy(() -> underTest.addBook(book)).isInstanceOf(BadRequestException.class)
				.hasMessageContainingAll("Book " + book.getName() + " is already exists!");

		verify(bookRepo, never()).save(book);
	}

//
//	/**
//	 * Test method for {@link com.revs.demo.junit.BookService#updateBook(com.revs.demo.junit.Book)}.
//	 */
//	@Test
//	void testUpdateBook() {
//		fail("Not yet implemented");
//	}
//
	/**
	 * Test method for {@link com.revs.demo.junit.BookService#removeBook(int)}.
	 */
	@Test
	void testRemoveBook() {

		int bookId = 0;
		Book book = new Book(1, "Hero", "Heroic Success", 30);

		assertThatThrownBy(() -> underTest.removeBook(bookId)).isInstanceOf(RuntimeException.class)
				.hasMessageContainingAll("Book id should not be Zero");

		int bookId1 = 1000;
		Optional<Book> optionalBook = Optional.ofNullable(null);

		when(bookRepo.findById(bookId1)).thenReturn(optionalBook);

		assertThatThrownBy(() -> underTest.getById(bookId1)).isInstanceOf(NoSuchElementException.class)
				.hasMessageContaining("No value present");

		verify(bookRepo, never()).delete(book);
	}

}
