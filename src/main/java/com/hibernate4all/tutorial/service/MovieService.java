package com.hibernate4all.tutorial.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	/**
	 * Exemple Dirty checking
	 * @param id
	 * @param description
	 */
	@Transactional // Spring va ouvrir une session avec la transaction
	public void updateDescription(Long id, String description) {
		Movie movie = repository.find(id);
		movie.setDescription(description); // Résultat : Hibernate met-à-jour toue l'entité c'est du dirty checking
	}
	
	@Transactional
	public List<Movie> addMovieTheGetAll() {
		Movie movie = new Movie();
		movie.setName("Fight Club");
		repository.persist(movie); // Flush auto
		return repository.getAll();
	}
}
