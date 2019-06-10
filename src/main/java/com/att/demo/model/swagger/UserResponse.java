package com.att.demo.model.swagger;

import com.att.demo.model.Account;
import com.att.demo.model.User;
import com.att.demo.model.representation.Resource;

/**
 * Swagger response class for the Account model object.
 * @param <T>
 *
 * @param <T>
 */
public class UserResponse extends Resource<User> {
	
    /**
     * This constructor sets the content of the Account Response 
     * 
     * @param content Command line parameters if any
     * 
     */
	public UserResponse(User content) {
		super(content);
	}

    /**
     * This method gets the content of the Account Response 
     * 
     * @return content of the user Response
     * 
     */
	@Override
	public User getContent() {
		return super.getContent();
	}

}