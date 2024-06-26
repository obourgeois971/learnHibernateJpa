package com.hibernate4all.tutorial.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hibernate4all.tutorial.config.PersistenceConfig;
import com.hibernate4all.tutorial.config.PersistenceConfigTest;
import com.hibernate4all.tutorial.domain.Movie;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceConfigTest.class })
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({"/datas/datas-test.sql"})
public class MovieServiceTest {

	@Autowired
	private MovieService movieService;
	
	/**
	 * Exemple Dirty checking
	 * @param id
	 * @param description
	 */
	@Test
	public void updateDescription_casNominal() {
		movieService.updateDescription(-2L, "super film mais k(ai oublié le pitch");
	}
	
	/**
	 * Tester le flush
	 */
	@Test
	public void testFlushMode() {
		List<Movie> movies = movieService.addMovieTheGetAll();
		assertThat(movies).as("on devrait récupérer les 2 entités en base + celle a ajoutée").hasSize(3);
	}
}
