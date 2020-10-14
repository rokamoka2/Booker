package com.booker.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Wardrobe {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;
    @OneToMany(mappedBy = "wardrobe")
    private Set<Shelf> shelfs;


    public Wardrobe() {
    }

    public Wardrobe(Long id, String name, User owner, Set<Shelf> shelfs) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.shelfs = shelfs;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<Shelf> getShelfs() {
        return this.shelfs;
    }

    public void setShelfs(Set<Shelf> shelfs) {
        this.shelfs = shelfs;
    }

    public Wardrobe id(Long id) {
        this.id = id;
        return this;
    }

    public Wardrobe name(String name) {
        this.name = name;
        return this;
    }

    public Wardrobe owner(User owner) {
        this.owner = owner;
        return this;
    }

    public Wardrobe shelfs(Set<Shelf> shelfs) {
        this.shelfs = shelfs;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", owner='" + getOwner() + "'" +
            ", shelfs='" + getShelfs() + "'" +
            "}";
    }
    
}