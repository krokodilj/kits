package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.timsedam.buildingmanagement.dto.UserDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.UserRegisterDTO;
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
	
	private String getAdminToken() {
		UserLoginDTO userLoginData = new UserLoginDTO("admin", "admin");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity("/api/auth/login", userLoginData, String.class);
		return responseEntity.getBody();
	}
	
	private HttpEntity<Object> getRequestEntity(Object params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", getAdminToken());
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
		return requestEntity;
	}

	/**
	 * POST request to "/api/users/" with valid UserRegisterDTO parameter
	 * Expected: new UserDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerUser() throws Exception {
		
		ResponseEntity<UserDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validUserRegisterDTO), UserDTO.class);
		
		UserDTO userFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validUserRegisterDTO.getUsername(), userFromResponse.getUsername());
		assertEquals(validUserRegisterDTO.getEmail(), userFromResponse.getEmail());
		assertEquals(validUserRegisterDTO.getPicture(), userFromResponse.getPicture());
		
		userRepository.delete(userFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/users/" twice with the same UserRegisterDTO parameter
	 * Expected: new UserDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerDuplicate() throws Exception {
		
		ResponseEntity<UserDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validUserRegisterDTO), UserDTO.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		UserDTO userFromResponse = responseEntity.getBody();
		
		responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validUserRegisterDTO), UserDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		userRepository.delete(userFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/users/" with invalid UserRegisterDTO parameter - username too short
	 * Expected: no UserDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerUsernameInvalid() throws Exception {
		
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setUsername("123");
		
		ResponseEntity<UserDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), UserDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
		
    }
	
	/**
	 * POST request to "/api/users/" with invalid UserRegisterDTO parameter - password too short
	 * Expected: no UserDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setPassword("123");
		
		ResponseEntity<UserDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), UserDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/users/" with invalid UserRegisterDTO parameter - email of invalid format
	 * Expected: no UserDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setEmail("INVALID_EMAIL_FORMAT");
		
		ResponseEntity<UserDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), UserDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	

}
