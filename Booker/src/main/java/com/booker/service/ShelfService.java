package com.booker.service;

import java.util.List;

import com.booker.domain.Book;
import com.booker.domain.Shelf;
import com.booker.repository.ShelfRepository;

import org.springframework.stereotype.Service;

@Service
public class ShelfService {

    private ShelfRepository repo;

    public void saveShelf(Shelf shelf){
        repo.save(shelf);
    }

    public List<Book> getBooksOnShelf(Long id){

        Shelf shelf = repo.findById(id).get();

        return (List<Book>) shelf.getBooks();
    }

    public Shelf getById(Long id){
        return repo.findById(id).get();
    }

    
}
