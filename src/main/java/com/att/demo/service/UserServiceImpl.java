package com.att.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.att.demo.model.Account;
import com.att.demo.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {
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
	
	public void saveUser(User user) {
		counter.incrementAndGet();
		users.add(user);
	}
	
	public boolean isUserExist(long userID) {
		for(User user : users){
			if(user.getId() == userID){
				return true;
			}
		}
		return false;
	}
	
	private static List<User> populateDummyUsers(){
		List<User> userList = new ArrayList<User>();
		userList.add(new User(1,"user1",11,100001));
		userList.add(new User(2,"user2",12,100002));
		userList.add(new User(3,"user3",13,100003));
		
		return userList;
	}

}
