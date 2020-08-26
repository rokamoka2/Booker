package com.booker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.booker.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	List<Role> findAll();
	
	Role findByName(String name);

}
