package com.booker.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.booker.domain.Book;
import com.booker.domain.Shelf;
import com.booker.domain.Wardrobe;

import org.springframework.data.repository.CrudRepository;

public interface ShelfRepository extends CrudRepository<Shelf, Long> {

    List<Shelf> findAll();

    Optional<Shelf> findById(Long id);

    List<Shelf> findByWardrobe(Wardrobe wardrobe);

    List<Book> findByBooks(Set<Book> books);
    
}
