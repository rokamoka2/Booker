package com.booker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.booker.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	
	List<Book> findAll();
	List<Book> findAllByOrderByIdDesc();
	
	List<Book> findByTitle(String title);
	List<Book> findByAuthor(String author);

}
