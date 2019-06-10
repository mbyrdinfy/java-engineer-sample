package com.att.demo.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.att.demo.exception.CustomError;
import com.att.demo.model.Account;
import com.att.demo.model.representation.Resource;
import com.att.demo.model.representation.ResourceCollection;
import com.att.demo.model.swagger.AccountResponse;
import com.att.demo.service.AccountService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This is the Controller class for Account mService
 * 
 * 
 */
@Controller
public class AccountResourceImpl implements AccountResource {
	
	public static final Logger logger = LoggerFactory.getLogger(AccountResourceImpl.class);

	@Autowired
	AccountService accountService; //Service which will do all data retrieval/manipulation work

	private static String baseUrl = "/accounts";

	@Override
	public Response findAllAccounts() {
		List<Account> accounts = accountService.findAllAccounts();		
		if (accounts == null) {
			return Response.noContent().build();
		}		
		Link link = Link.fromUri(baseUrl).rel("self").build();		
		ResourceCollection<Account> resource = new ResourceCollection<>(accounts);
		return Response.ok(resource).links(link).build();
	}	
	
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
	@Override
	public Response getAccount(@PathParam("AccountID") long AccountID) {
		System.out.println("Account ID is " + AccountID);
		Account accountReturn = accountService.findById(AccountID);
		if (accountReturn == null) {
			System.out.println("accountreturn is null");
			return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Account with ID " + AccountID + " not found\"}").build();
		}
		Link link = Link.fromUri(baseUrl+ "/" + AccountID).rel("self").build();	
		System.out.println(baseUrl + "/" + AccountID);
		System.out.println("accountName is " + accountReturn.getName());
		Resource<Account> resource = new Resource<>(accountReturn);
		return Response.ok(resource).build();
		
	}
	
	@POST
	@Path("/")
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
					.entity("{\"error\":\"Unable to create. A Account with given name already exist\"}")
					.build();
		accountService.saveAccount(account);
		return Response.status(Response.Status.CREATED).build();
	}
	

	

	

}