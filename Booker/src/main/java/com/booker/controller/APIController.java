package com.booker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booker.domain.Book;
import com.booker.service.BookService;

@RestController
@RequestMapping(path = "/api")
public class APIController {
	
	@Autowired
	private BookService bs;
	
	@GetMapping(path = "/book/allbooks", produces = "application/json")
	public List<Book> getAllBooksToAPI(){
		return bs.getBooks();
	}
	
	@GetMapping(path = "/book/{ID}", produces = "application/json")
	public Book getBookByIDToAPI(@PathVariable(value="ID") Long id){
		return bs.FindById(id);
	}

}
