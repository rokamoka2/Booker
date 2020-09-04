package com.booker.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="books")
public class Book {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private Long molyId;
	private String isbn;
	private String title;
	private String author;
	@Column(nullable = true)
	private int releaseYear;
	private String genre;
	private String series;
	private String place;
	private Date notedDate;
	@ManyToMany(mappedBy = "books")
	private Set<User> users = new HashSet<User>();
		
	//Constructor
	public Book() {
	}
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getReleaseYear() {
		return releaseYear;
	}
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public Date getNotedDate() {
		return notedDate;
	}

	public void setNotedDate(Date notedDate) {
		this.notedDate = notedDate;
	}

	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	@JsonProperty
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Long getMolyId() {
		return molyId;
	}

	public void setMolyId(Long molyId) {
		this.molyId = molyId;
	}
	
	

}
