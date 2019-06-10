package com.att.demo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.att.demo.model.Account;
import com.att.demo.model.User;
import com.att.demo.model.representation.Resource;
import com.att.demo.model.swagger.AccountResponse;
import com.att.demo.model.swagger.UserResponse;
import com.att.demo.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class UserResourceImpl implements UserResource {

	@Autowired
	UserService userService;
	
	private static String baseUrl = "/users";
	
	@GET
	@Path("/{UserID}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(
			value = "Get User by ID",
			notes = "Returns the user that contains the given User ID in Resource representation format",
			response = UserResponse.class)
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "OK"),
					@ApiResponse(code = 404, message = "Not Found")					
					})
	@Override
	public Response getUserByID(@PathParam("UserID") long userID) {
		User userReturn = userService.getUserById(userID);
		if (userReturn == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"User with ID " + userID + " not found\"}").build();
		}
		Link link = Link.fromUri(baseUrl+ "/" + userID).rel("self").build();	
		Resource<User> resource = new Resource<>(userReturn);
		return Response.ok(resource).links(link).build();
	}
	
	@POST
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(
			value = "Get User by ID",
			notes = "Returns the user that contains the given User ID in Resource representation format",
			response = UserResponse.class)
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "OK"),
					@ApiResponse(code = 409, message = "Conflict")					
					})
	@Override
	public Response createUser(User user) {
		if(userService.isUserExist(user.getId()))
			return Response.status(Response.Status.CONFLICT)
					.entity("{\"error\":\"Unable to create. A User with given ID already exist\"}")
					.build();
		userService.saveUser(user);
		return Response.status(Response.Status.CREATED).build();
	}
}
