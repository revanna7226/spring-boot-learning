package com.revs.junit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revs.junit.entity.Movie;

public interface IMovieRepository extends JpaRepository<Movie, Long>{
	
	List<Movie> findByGeneraAndName(String genera, String name);

}
