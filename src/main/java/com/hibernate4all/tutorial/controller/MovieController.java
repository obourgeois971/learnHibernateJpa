package com.hibernate4all.tutorial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.repository.MovieRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieRepository repository;
	
	@PostMapping("/")
	public Movie create(@RequestBody Movie movie) {
		repository.persist(movie);
		return movie;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Movie> get(@PathVariable("id") Long id) {
		Movie movie = repository.find(id);
		if(movie == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(movie);
		}
	}
	
}
