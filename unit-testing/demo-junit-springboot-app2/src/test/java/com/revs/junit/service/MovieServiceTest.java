package com.revs.junit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revs.junit.entity.Movie;
import com.revs.junit.repository.IMovieRepository;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
	
	@Mock IMovieRepository movieRepo;
	
	@InjectMocks MovieService underTest;

	@Test
	void test_save() {
		Movie vkMovie = new Movie(1L,	"Vikranth Rona", "Adventure", LocalDate.of(2022, 12, 02));
		when(movieRepo.save(any(Movie.class))).thenReturn(vkMovie);
		Movie newMovie = underTest.save(vkMovie);
		assertThat(newMovie).isNotNull();
		assertThat(newMovie.getName()).isEqualTo("Vikranth Rona");
	}
	
	@Test
	void test_getAllMovies() {
		Movie vkMovie = new Movie(1L,	"Vikranth Rona", "Adventure", LocalDate.of(2022, 12, 02));
		Movie krantiMovie = new Movie(2L,	"Kranti", "Action, Love", LocalDate.of(2023, 01, 02));
		
		List<Movie> movieList = new ArrayList<>();
		movieList.add(vkMovie);
		movieList.add(krantiMovie);
		
		when(movieRepo.findAll()).thenReturn(movieList);
		
		List<Movie> fetchedMovieList = underTest.getAllMovies();
		
		assertThat(fetchedMovieList).isNotNull();
		assertThat(fetchedMovieList.size()).isEqualTo(2);
	}
	
	

}
