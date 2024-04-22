package com.hibernate4all.tutorial.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hibernate4all.tutorial.config.PersistenceConfig;
import com.hibernate4all.tutorial.domain.Movie;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({"/datas/datas-test.sql"})
public class MovieRepositoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryTest.class);
	
	@Autowired
	private MovieRepository repository;
	
	@Test
	public void save_casNominal() {
		Movie movie = new Movie();
		movie.setName("Inception");
		repository.persist(movie);
		System.out.println("Fin de test");
	}
	
	@Test
	public void merge_casSimple() {
		Movie movie = new Movie();
		movie.setName("InceptionUpdate");
		movie.setId(-1L);
		Movie mergeMovie = repository.merge(movie);
		assertThat(mergeMovie.getName()).as("le nom du film n'a pas été mis à jour").isEqualTo("InceptionUpdate");
	}
	
	@Test
	public void find_casNominal() {
		Movie movie = repository.find(-2L);
		assertThat(movie.getName()).as("mauvais film récupéré").isEqualTo("Memento");
	}
	
	@Test
	public void getAll_casNominal() {
		List<Movie> movies = repository.getAll();
		assertThat(movies).as("l'ensemble des films n'a pas été récupéré").hasSize(2);
	}
	
	@Test
	public void remove_casNominal() {
		repository.remove(-2L);
		List<Movie> movies = repository.getAll();
		assertThat(movies).as("le film n'a pas été supprimé").hasSize(1);
	}
	
	@Test
	public void getReference_casNominal() {
		Movie movie = repository.getReference(-2L);
		assertThat(movie.getId()).as("la référence n'a pas été correctement chargée").isEqualTo(-2L);
	}
	
	@Test
	public void getReference_fail() {
		assertThrows(LazyInitializationException.class, () -> {
			Movie movie = repository.getReference(-2L);
			LOGGER.trace("movie name : " + movie.getName());
			assertThat(movie.getId()).as("la référence n'a pas été correctement chargée").isEqualTo(-2L);
		});
	}
}
