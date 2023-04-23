package com.revs.demo.junit;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Description;

@DataJpaTest
@Description(value = "Youtube URL: https://youtu.be/Geq60OVyBPg")
class IBookRepositoryTest {

	@Autowired
	private IBookRepository underTest;

//	@AfterEach
//	void tearDown() {
//		underTest.deleteAll();
//	}

	@Test
	void testFindBookByName() {
		String bookName = "Atomic Habits";

		boolean exists = underTest.findBookByName(bookName);

		assertThat(exists).isTrue();
		exists = underTest.findBookByName("XYZ");

		assertThat(exists).isTrue();

	}

	@Test
	void testForFindByBookByNameIfNotPresent() {
		String bookName = "REVS";

		boolean exists = underTest.findBookByName(bookName);

		assertThat(exists).isFalse();
	}

	@Test
	void testForFindAll() {
		List<Book> books = underTest.findAll();

		assertThat(books).size().isGreaterThan(4);
	}

}