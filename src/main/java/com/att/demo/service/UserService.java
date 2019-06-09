package com.att.demo.service;

import com.att.demo.model.User;

public interface UserService {
	
	public User getUserbyId(long Id);
	
	public void createUser(User user);

}
