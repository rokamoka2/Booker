package com.booker.service;

import com.booker.domain.User;

public interface UserService {
	
	public User findByUsername(String userName);

}
