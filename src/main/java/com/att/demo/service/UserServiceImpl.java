package com.att.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.att.demo.model.User;

public class UserServiceImpl {
private static final AtomicLong counter = new AtomicLong();
	
	private static List<User> users;
	
	static{
		users = populateDummyUsers();
	}
	public User getUserById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	private static List<User> populateDummyUsers(){
		List<User> userList = new ArrayList<User>();
		userList.add(new User(1,"user1",11,100001));
		userList.add(new User(2,"user2",12,100002));
		userList.add(new User(3,"user3",13,100003));
		
		return userList;
	}

}
