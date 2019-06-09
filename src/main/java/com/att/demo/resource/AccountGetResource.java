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

import com.att.demo.model.Account;
import com.att.demo.model.representation.Resource;
import com.att.demo.model.swagger.AccountResponse;
import com.att.demo.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("account")
@Path("/get")
@Produces({MediaType.APPLICATION_JSON})
public class AccountGetResource {
	
	@Autowired
	AccountService accountService; //Service which will do all data retrieval/manipulation work

	@GET
	@Path("/{AccountID}")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(
			value = "Get Account by ID",
			notes = "Returns the account that contains the given Account ID in Resource representation format",
			response = AccountResponse.class)
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "OK"),
					@ApiResponse(code = 404, message = "Not Found")					
					})
	public Response getAccount(@PathParam("AccountID") long AccountID) {
		Account accountReturn = accountService.findById(AccountID);
		if (accountReturn == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Account with ID " + AccountID + " not found").build();
		}
		Resource<Account> resource = new Resource<>(accountReturn);
		return Response.ok(resource).build();
	}
	
	@POST
	@Path("/create")
	@Produces({MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_JSON})
	@ApiOperation(
			value = "Get Account by ID",
			notes = "Returns the account that contains the given Account ID in Resource representation format",
			response = AccountResponse.class)
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "OK"),
					@ApiResponse(code = 409, message = "Unable to create. A Account with given name already exist")					
					})
	public Response createAccount(Account account) {
		if(accountService.isAccountExist(account))
			return Response.status(Response.Status.CONFLICT)
					.entity("Unable to create. A Account with given name already exist")
					.build();
		accountService.saveAccount(account);
		return Response.status(Response.Status.CREATED).build();
	}
}
