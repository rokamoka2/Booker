package com.booker.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@GeneratedValue
	@Id
	private Long id;
	@Column( unique=true, nullable=false )
	private String username;
	@Column( unique=true, nullable=false )
	private String email;
	private int age;
	@Column(nullable=false)
	private String password;
	
	@ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinTable( name = "users_roles", joinColumns = {@JoinColumn(name="user_id")}, inverseJoinColumns = {@JoinColumn(name="role_id")})
	private Set<Role> roles = new HashSet<Role>();

	@OneToMany(mappedBy = "owner")
	private Set<Wardrobe> wardrobes;
	
	
	public User() {}	
		
	public User(String name, int age) {
		super();
		this.username = name;
		this.age = age;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Wardrobe> getWardrobes() {
		return this.wardrobes;
	}

	public void setWardrobes(Set<Wardrobe> wardrobes) {
		this.wardrobes = wardrobes;
	}

}