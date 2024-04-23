package com.hibernate4all.tutorial.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hibernate4all.tutorial.domain.Movie;

@Repository
public class MovieRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepository.class);

	@PersistenceContext
	EntityManager entityManager;
	
	// 1-Etat JPA - Persist, Detach et Flush
	@Transactional
	public void persist(Movie movie) {
		// throw new UnsupportedOperationException();
		LOGGER.trace("avant entityManager.contains() " + entityManager.contains(movie)); //  false
		entityManager.persist(movie);
		LOGGER.trace("après entityManager.contains() " + entityManager.contains(movie)); // true
		// entityManager.detach(movie);
		// LOGGER.trace("après entityManager.contains() " + entityManager.contains(movie)); // false
		// entityManager.flush();
	}
	
	// 1-Premiers pas avec hibernate
	public List<Movie> getAll() {
		// throw new UnsupportedOperationException();
		return entityManager.createQuery("from Movie", Movie.class).getResultList();
	}
	
	// 2-Etats des entités
	public Movie find(Long id) {
		Movie result = entityManager.find(Movie.class, id);
		LOGGER.trace("avant entityManager.contains() " + entityManager.contains(result));
		return result;
		
	}
	
	@Transactional
	public Movie merge(Movie movie) {
		return entityManager.merge(movie);
	}
	
	/** 
	 * Traitement du cache
	 * @param id
	 */
	@Transactional
	public void remove(Long id) {
		Movie movie1 = entityManager.find(Movie.class, id);
		Movie movie2 = entityManager.find(Movie.class, id);
		Movie movie3 = entityManager.find(Movie.class, id);
		Movie movie4 = entityManager.find(Movie.class, id);
		entityManager.remove(movie1);
	}
	
	// @Transactional
	public Movie getReference(Long l) {
		// Movie result = entityManager.getReference(Movie.class, l);
		// LOGGER.trace("movie name : " + result);
		return entityManager.getReference(Movie.class, l);
	}
}
