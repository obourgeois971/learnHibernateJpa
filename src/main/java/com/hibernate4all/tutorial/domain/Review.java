package com.hibernate4all.tutorial.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	private String author;

	private String content;

	@ManyToOne(fetch = FetchType.LAZY) // qui signifie que plusieurs Review peuvent être associé à un seul Movie
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public Review setAuthor(String author) {
		this.author = author;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Review setContent(String content) {
		this.content = content;
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hash(32);
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Movie)) {
			return false;
		}
		Review other = (Review) obj;
		if (id == null && other.getId() == null) {
			return Objects.equals(author, other.getAuthor()) && Objects.equals(content, other.getContent());
		}
		return id != null && Objects.equals(id, other.getId());
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", author=" + author + ", content=" + content + "]";
	}
}
