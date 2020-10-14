package com.booker.repository;

import java.util.List;
import java.util.Optional;

import com.booker.domain.User;
import com.booker.domain.Wardrobe;

import org.springframework.data.repository.CrudRepository;

public interface WardrobeRepository extends CrudRepository<Wardrobe, Long> {

    List<Wardrobe> findAll();

    Optional<Wardrobe> findById(Long id);

    List<Wardrobe> findByName(String name);

    List<Wardrobe> findByOwner(User owner);

    
    
}
