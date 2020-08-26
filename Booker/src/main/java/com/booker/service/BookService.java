package com.booker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booker.domain.Book;
import com.booker.repository.BookRepository;

@Service
public class BookService {
	private BookRepository bookRepo;
	
	public List<Book> getBooks(){
		return bookRepo.findAllByOrderByIdDesc();
	}
	
	public void SaveBook(Book book) {
		bookRepo.save(book);
	}
	
	public Book FindById(Long id) {
		Optional<Book> optionalBook = bookRepo.findById(id);
		Book book = optionalBook.get();
		return book;
	}
	
	public List<Book> getSpecificBook(String searched){
		List<Book> result = bookRepo.findByAuthor(searched);
		result.addAll(bookRepo.findByTitle(searched));
		return result;
		
	}
	
	public void UpdateBook(Book updatedBook) {
		Optional<Book> optionalBook = bookRepo.findById(updatedBook.getId());
		Book bookToUpdate = optionalBook.get();
		bookToUpdate.setAuthor(updatedBook.getAuthor());
		bookToUpdate.setGenre(updatedBook.getGenre());
		bookToUpdate.setPlace(updatedBook.getPlace());
		bookToUpdate.setReleaseYear(updatedBook.getReleaseYear());
		bookToUpdate.setSeries(updatedBook.getSeries());
		bookToUpdate.setTitle(updatedBook.getTitle());
		bookRepo.save(bookToUpdate);
	}
	
	public void DeleteBook(Book bookToDelete) {
		bookRepo.delete(bookToDelete);
		}
	
	public BookRepository getBookRepo() {
		return bookRepo;
	}
	@Autowired
	public void setBookRepo(BookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}
	
	
}
