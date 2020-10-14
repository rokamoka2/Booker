package com.booker.service;

import java.util.List;

import com.booker.domain.User;
import com.booker.domain.Wardrobe;
import com.booker.repository.WardrobeRepository;

import org.springframework.stereotype.Service;

@Service
public class WardrobeService {

    private WardrobeRepository repo;

    List<Wardrobe> getAllWardrobesByUser(User user){

        return repo.findByOwner(user);

    }

    void saveWardrobe(Wardrobe wardrobe){
        repo.save(wardrobe);
    }
    
}
