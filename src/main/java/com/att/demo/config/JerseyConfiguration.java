package com.att.demo.config;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.att.demo.resource.AccountResourceImpl;
import com.att.demo.resource.UserResourceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;


/**
 * This Class reads and sets all configuration related to Jersey
 * 
 * 
 */
@Component
@ApplicationPath("/")
public class JerseyConfiguration extends ResourceConfig {
	private static final Logger log = Logger.getLogger("com.att.idp.logging.ServerLogger");
	
	 @Value("${spring.jersey.application-path}")
	  private String apiPath;
	
    /**
     * This method creates an objectmapper and sets configuration related
     * to serialization 
     * 
     * @return objectMapper
     */
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES);
		objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return objectMapper;
	}


    /**
     * This is the main constructor which register the Jersey configs 
     * 
     */
	@Autowired
    public JerseyConfiguration() {
		register(AccountResourceImpl.class);	
		
    }
	
		@PostConstruct
	  public void init() {
	    // Register components where DI is needed
	    this.configureSwagger();
	  }
	
	private void configureSwagger() {
	    // Available at localhost:port/api/swagger.json
	    this.register(ApiListingResource.class);
	    this.register(SwaggerSerializers.class);

	    BeanConfig config = new BeanConfig();
	    config.setConfigId("springboot-demo");
	    config.setTitle("Swagger docs for Demo application");
	    config.setVersion("v1");
	    config.setContact("Ravi Bhusarapu");
	    config.setSchemes(new String[] { "http", "https" });
	    config.setBasePath(this.apiPath);
	    config.setResourcePackage("com.att.demo.resource");
	    config.setPrettyPrint(true);
	    config.setScan(true);
	  }

    /**
     * This method returns a REST client instance 
     * 
     * @return jerseyClient REST Client
     * 
     */
	@Bean
	public Client jerseyClient() {
		return ClientBuilder.newClient(
				new ClientConfig());
	}
}