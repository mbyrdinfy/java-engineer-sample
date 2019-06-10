package com.att.demo.resource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import com.att.demo.model.User;

import io.swagger.annotations.Api;

/**
 * This is the Interface definition for Account mService
 * 
 * 
 */
@Api("users")
@Path("/users")
public interface UserResource {
	
	@Path("/")
	public Response getUserByID(@PathParam("UserID") long userID);
	
	@Path("/")
	public Response createUser(User user);
	

}