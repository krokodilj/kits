package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.timsedam.buildingmanagement.dto.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String URL_PREFIX = "/api/users/";
	
	private UserRegisterDTO validUserRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", "picture.png");
	
	/**
	 * POST request to "/api/users/" with valid UserDTO parameter
	 * Expected: new User is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerManager() throws Exception {
		
		ResponseEntity<User> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "admin", validUserRegisterDTO, User.class);
		
		User userFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validUserRegisterDTO.getUsername(), userFromResponse.getUsername());
		assertEquals(validUserRegisterDTO.getEmail(), userFromResponse.getEmail());
		assertEquals(validUserRegisterDTO.getPicture(), userFromResponse.getPicture());
		
		userRepository.delete(userFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/users/" twice with the same UserDTO parameter
	 * Expected: new User is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerDuplicate() throws Exception {
		
		ResponseEntity<User> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "admin", validUserRegisterDTO, User.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		User userFromResponse = responseEntity.getBody();
		
		responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "admin", validUserRegisterDTO, User.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		userRepository.delete(userFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/users/admin" with invalid UserDTO parameter - username too short
	 * Expected: no User is returned, HTTP Status 422
	 */
	@Test
	public void registerUsernameInvalid() throws Exception {
		
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setUsername("123");
		
		ResponseEntity<User> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "admin", requestObject, User.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
		
    }
	
	/**
	 * POST request to "/api/users/admin" with invalid UserDTO parameter - password too short
	 * Expected: no User is returned, HTTP Status 422
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setPassword("123");
		
		ResponseEntity<User> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "admin", requestObject, User.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/users/admin" with invalid UserDTO parameter - email of invalid format
	 * Expected: no User is returned, HTTP Status 422
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setEmail("INVALID_EMAIL_FORMAT");
		
		ResponseEntity<User> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "admin", requestObject, User.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/users/INVALID_PATH" with valid UserDTO parameter
	 * Expected: no User is returned, HTTP Status 422
	 */
	@Test
	public void registerUserTypeInvalid() throws Exception {
		
		ResponseEntity<User> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "INVALID_PATH", validUserRegisterDTO, User.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
	}
	

}
