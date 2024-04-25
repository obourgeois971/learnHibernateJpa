package com.hibernate4all.tutorial.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.repository.MovieRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


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
		// ou autre méthod
		// Movie movie = repository.getReference(id);
		if(movie == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(movie);
		}
	}
	
	@PutMapping("/")
	public ResponseEntity<Movie> update (@RequestBody Movie movie) {
		Optional<Movie> result = repository.update(movie);
		if(result.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(result.get());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Movie> delete(@PathVariable("id") Long id) {
		boolean removed = repository.remove(id);
		return removed ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
	}
}
