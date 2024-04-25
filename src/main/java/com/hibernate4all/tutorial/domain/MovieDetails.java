package com.hibernate4all.tutorial.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_details")
public class MovieDetails {

	@Id
	private Long id;
	
	@Column(length = 4000)
	private String plot;
	
	@OneToOne
	@MapsId
	private Movie movie;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlot() {
		return plot;
	}
	public MovieDetails setPlot(String plot) {
		this.plot = plot;
		return this;
	}
	public Movie getMovie() {
		return movie;
	}
	public MovieDetails setMovie(Movie movie) {
		this.movie = movie;
		return this;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(58);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if(!(obj instanceof Movie)) {
			return false;
		}
		MovieDetails other = (MovieDetails) obj;
		if(id == null && other.getId() == null) {
			return Objects.equals(plot, other.getPlot());
		}
		return id != null && Objects.equals(id, other.getId());
	}
	
	@Override
	public String toString() {
		return "MovieDetails [id=" + id + ", plot=" + plot + ", movie=" + movie + "]";
	}
}
