package com.booker.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.github.javafaker.Faker;

@Entity
@Table(name="books")
public class Book {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	private String title;
	private String author;
	@Column(nullable = true)
	private int releaseYear;
	private String genre;
	private String series;
	private String place;
	private Date notedDate;
	@ManyToOne
	private User user;
	
	
	public void Fake(User user) {
		Faker faker = new Faker();
		this.setAuthor(faker.book().author());
		this.setNotedDate(new Date());
		this.setGenre(faker.book().genre());
		this.setReleaseYear(2003);
		this.setPlace("Polc");
		this.setSeries("Kamu könyvek");
		this.setTitle(faker.book().title());
		this.setUser(user);
		
	}
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	

	

}
