package com.booker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booker.domain.Role;
import com.booker.repository.RoleRepository;

@Service
public class RoleService {
	
	private RoleRepository roleRepo;
	
	public List<Role> getRoles(){
		return roleRepo.findAll();
	}
	
	public Role getSpecificRole(String name) {
		return roleRepo.findByName(name);
	}

	public RoleRepository getRoleRepo() {
		return roleRepo;
	}
	
	public void Save(Role role) {
		roleRepo.save(role);
	}

	@Autowired
	public void setRoleRepo(RoleRepository roleRepo) {
		this.roleRepo = roleRepo;
	}
	
	

}
