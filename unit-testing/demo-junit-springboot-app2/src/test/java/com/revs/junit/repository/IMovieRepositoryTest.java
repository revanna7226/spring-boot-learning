package com.revs.junit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.revs.junit.entity.Movie;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
@Order(1)
class IMovieRepositoryTest {

	@Autowired
	private IMovieRepository movieRepo;
	
	private Movie avatarMovie;
	private Movie titanicMovie;
	
	@BeforeAll
	void init() {
		avatarMovie = new Movie();
		avatarMovie.setName("Avatar");
		avatarMovie.setGenera("Adventure");
		avatarMovie.setReleaseDate(LocalDate.of(2009, Month.APRIL, 2));
		
		titanicMovie = new Movie();
		titanicMovie.setName("Titanic");
		titanicMovie.setGenera("Fantacy");
		titanicMovie.setReleaseDate(LocalDate.of(2009, Month.APRIL, 2));
	}
	
	@AfterEach
	void tearDown() {
		movieRepo.deleteAll();
	}

	@Test
	@DisplayName("It should save movie to DB")
	void test_saveMethod() {
		// Arrange

		// Act
		Movie savedMovie = movieRepo.save(avatarMovie);

		// Assert
		assertNotNull(savedMovie);
		assertThat(savedMovie.getId()).isNotNull();

		// Failed Expected 2L but returned 1L
		// assertThat(savedMovie.getId()).isEqualTo(2);
	}

	@Test
	@DisplayName("Get all Movies with size of 2")
	void test_getAllMovies() {
		// Arrange
		movieRepo.save(avatarMovie);
		movieRepo.save(titanicMovie);

		// Act
		List<Movie> movieList = movieRepo.findAll();

		// Assert
		assertThat(movieList).isNotNull();
		assertThat(movieList.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("Get Moview By ID")
	void test_getMoviebyID() {
		Movie savedMovie = movieRepo.save(avatarMovie);

		Optional<Movie> movie = movieRepo.findById(savedMovie.getId());

		assertThat(movie.isPresent()).isEqualTo(true);
		assertThat(movie.get().getName()).isEqualTo(avatarMovie.getName());
	}

	@Test
	@DisplayName("update name of Movie with Fantacy")
	void test_updateMovieName() {
		Movie savedMovie = movieRepo.save(avatarMovie);

		Movie fetchedMovie = movieRepo.findById(savedMovie.getId()).get();

		fetchedMovie.setGenera("Fantacy");

		Movie updatedMovie = movieRepo.save(fetchedMovie);

		assertThat(updatedMovie).isNotNull();
		assertThat(updatedMovie.getGenera()).isEqualTo("Fantacy");
	}

	@Test
	@DisplayName("It should test delete function")
	void test_removeMovie() {
		movieRepo.save(avatarMovie);

		Movie savedTitanicMovie = movieRepo.save(titanicMovie);
		Long movieId = savedTitanicMovie.getId();

		movieRepo.deleteById(movieId);

		List<Movie> movieList = movieRepo.findAll();

		Optional<Movie> deletedMovie = movieRepo.findById(movieId);

		assertThat(movieList).isNotNull();
		assertThat(movieList.size()).isEqualTo(1);
		assertThat(deletedMovie).isEmpty();
	}

	@Test
	@Order(1)
	@DisplayName("I should find Movie by Genera and Name")
	void test_findByGeneraAndName() {
		// Arrange
		movieRepo.save(avatarMovie);
		
		List<Movie> movieList =  movieRepo.findByGeneraAndName(avatarMovie.getGenera(), avatarMovie.getName());
		
		assertThat(movieList).isNotNull();
		assertThat(movieList.size()).isEqualTo(1);
	}

}
