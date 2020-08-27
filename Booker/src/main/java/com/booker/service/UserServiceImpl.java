package com.booker.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booker.domain.Role;
import com.booker.domain.User;
import com.booker.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	private PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	private final String defaRole = "user";
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}
	

	public User FindByID(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public void addUser(User newuser) {
		// TODO Auto-generated method stub
		newuser.setPassword(encoder.encode(newuser.getPassword()));
		Set<Role> roles = new HashSet<Role>();
		if (roleService.getSpecificRole(defaRole) != null) {
			roles.add(roleService.getSpecificRole(defaRole));
		} else {
			Role newrole = new Role();
			newrole.setName(defaRole);
			roleService.Save(newrole);
			roles.add(roleService.getSpecificRole(defaRole));
		}
		
		newuser.setRoles(roles);
		userRepository.save(newuser);
		
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
		
	}
	
	public void DeleteUser(User userToDelete) {
		userRepository.delete(userToDelete);
	}
	
	
	

}
