package com.att.demo.service;

import java.util.List;

import com.att.demo.model.User;

public interface UserService {
	
	public User getUserById(long Id);
	
	public void saveUser(User user);
	
	public boolean isUserExist(long userID);

}
