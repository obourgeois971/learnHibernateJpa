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
import com.hibernate4all.tutorial.config.PersistenceConfigTest;
import com.hibernate4all.tutorial.domain.Certification;
import com.hibernate4all.tutorial.domain.Genre;
import com.hibernate4all.tutorial.domain.Movie;
import com.hibernate4all.tutorial.domain.MovieDetails;
import com.hibernate4all.tutorial.domain.Review;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceConfigTest.class })
@SqlConfig(dataSource = "dataSourceH2", transactionManager = "transactionManager")
@Sql({ "/datas/datas-test.sql" })
public class MovieRepositoryTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieRepositoryTest.class);

	@Autowired
	private MovieRepository repository;

	@Test
	public void save_casNominal() {
		Movie movie = new Movie()
				.setName("Inception")
				.setCertification(Certification.INTERDIT_MOINS_12);
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
		assertThat(movie.getCertification()).as("le converter n'a pas fonctionné")
				.isEqualTo(Certification.INTERDIT_MOINS_12);
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
	
	
	// Test avec l'entity Review
	@Test
	public void asswociationSave_casNominal() {
		Movie movie = new Movie().setName("Fight Club")
								.setCertification(Certification.INTERDIT_MOINS_12)
								.setDescription("Le fight club n'existe pas");
		Review review1 = new Review().setAuthor("max").setContent("super film !");
		Review review2 = new Review().setAuthor("jp").setContent("au top !");
		
		movie.addReview(review1);
		movie.addReview(review2);
		
		// équivalent à : 
		// review1.setMovie(movie);
		// review2.setMovie(movie);
		// movie.getReviews().add(review1);
		// movie.getReviews().add(review2);
		
		repository.persist(movie);
	}
	
	// Test de la levée d'une lazy initialisation
	@Test
	public void associationGet_casNominal() {
		assertThrows(LazyInitializationException.class, () -> {
			Movie movie = repository.find(-1L);
			LOGGER.trace("nombre de reviews : " + movie.getReviews().size());
		});
	}
	
	@Test
	public void save_withGenres() {
		Movie movie = new Movie().setName("The Social Network");
		
		Genre bio = new Genre().setName("Biologie");
		Genre drama = new Genre().setName("Drama");
		
		movie.addGenre(bio).addGenre(drama);
		
		repository.persist(movie);
		
		assertThat(bio.getId()).as("l'entité Genre aurait du être persistée").isNotNull();
	}
	
	@Test
	public void save_withExistingGenre() {
		Movie movie = new Movie().setName("The Social Network");
		
		Genre bio = new Genre().setName("Biologie");
		Genre drama = new Genre().setName("Drama");
		Genre action = new Genre().setName("Action");
		action.setId(-1L);
		
		movie.addGenre(bio).addGenre(drama).addGenre(action);
		
		repository.persist(movie);
		
		assertThat(bio.getId()).as("l'entité Genre aurait du être persistée").isNotNull();
	}
	
	@Test
	public void addMovieDetails_casNominal() {
		MovieDetails movieDetails = new MovieDetails().setPlot("Intrigue du film Memento trés longue !");
		repository.addMovieDetails(movieDetails, -2L);
		assertThat(movieDetails.getId()).as("l'entité MovieDetails aurait du être persistée").isNotNull();
	}
}
