package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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

import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.request.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ManagerControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String URL = "/api/managers/";
		
	private UserRegisterDTO validUserRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
	
	private String getToken(String username, String password) {
		UserLoginDTO userLoginData = new UserLoginDTO(username, password);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity("/api/auth/login", userLoginData, String.class);
		return responseEntity.getBody();
	}
	
	private HttpEntity<Object> getRequestEntity(Object params, String username, String password) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", getToken(username, password));
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
		return requestEntity;
	}
	
	/**
	 * POST request to "/api/managers/" with valid UserRegisterDTO parameter
	 * Expected: new Manager's id is returned, HTTP Status 201
	 */
	@Test
	public void registerManager() throws Exception {
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(validUserRegisterDTO, "admin1", "admin1"), Long.class);
			
		Long adminId = responseEntity.getBody();
		User admin = userRepository.findOne(adminId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validUserRegisterDTO.getUsername(), admin.getUsername());
		assertEquals(validUserRegisterDTO.getEmail(), admin.getEmail());
		
		userRepository.delete(adminId);
    }
	
	/**
     * POST request to "/api/managers/" sent by unauthorised user
     * Expected: HttpStatus.FORBIDDEN
     */
    @Test
    public void registerManagerUnauthorised(){

        ResponseEntity<?> responseEntity = 
        		restTemplate.postForEntity(URL, getRequestEntity(validUserRegisterDTO, "resident1", "resident1"), Object.class);

        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/managers/" twice with the same UserRegisterDTO parameter
	 * Expected: error message is returned, HTTP Status 404
	 */
	@Test
	public void registerDuplicate() throws Exception {
		ResponseEntity<Long> responseEntityValidRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(validUserRegisterDTO, "admin1", "admin1"), Long.class);

		Long adminId = responseEntityValidRequest.getBody();
		assertEquals(HttpStatus.CREATED, responseEntityValidRequest.getStatusCode());
		
		ResponseEntity<String> responseEntityDuplicateRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(validUserRegisterDTO, "admin1", "admin1"), String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntityDuplicateRequest.getStatusCode());
		assertEquals("Manager with username: USERNAME already exists.", responseEntityDuplicateRequest.getBody());
		
		userRepository.delete(adminId);
    }
	
	/**
	 * POST request to "/api/managers/" with invalid UserRegisterDTO - username missing
	 * Expected: error message is returned, HTTP Status 422
	 */
	@Test
	public void registerUsernameMissing() throws Exception {
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setUsername(null);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'username' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/managers/" with invalid UserRegisterDTO parameter - username too short
	 * Expected: error message is returned, HTTP Status 422
	 */
	@Test
	public void registerUsernameInvalid() throws Exception {
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setUsername("123");
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'username' must be at least 4 characters long", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/managers/" with invalid UserRegisterDTO parameter - password missing
	 * Expected: error message is returned, HTTP Status 422
	 */
	@Test
	public void registerPasswordMissing() throws Exception {
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setPassword(null);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'password' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/managers/" with invalid UserRegisterDTO parameter - password too short
	 * Expected: error message is returned, HTTP Status 422
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setPassword("123");
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'password' must be at least 6 characters long", responseEntity.getBody());
    }

	/**
	 * POST request to "/api/managers/" with invalid UserRegisterDTO parameter - email of invalid format
	 * Expected: no AdminDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		UserRegisterDTO requestObject = new UserRegisterDTO(validUserRegisterDTO);
		requestObject.setEmail("INVALID_EMAIL_FORMAT");
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'email' must be of valid format", responseEntity.getBody());
    }
	
}
	