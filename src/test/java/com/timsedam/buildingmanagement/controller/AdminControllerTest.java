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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {
	
	private static final String URL = "/api/admins/";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
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
	 * POST request to "/api/admins/" with valid UserRegisterDTO parameter
	 * Expected: new Admin's id is returned, HTTP Status 201 CREATED
	 */
	@Test
	public void registerAdmin() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), Long.class);
			
		Long adminId = responseEntity.getBody();
		User admin = userRepository.findOne(adminId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(userRegisterDTO.getUsername(), admin.getUsername());
		assertEquals(userRegisterDTO.getEmail(), admin.getEmail());
		
		userRepository.delete(adminId);
    }
	
    /**
     * POST request to "/api/admins/" sent by unauthorised user
     * Expected: HttpStatus 403 FORBIDDEN
     */
    @Test
    public void registerAdminUnauthorised(){
    	UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
        ResponseEntity<?> responseEntity = 
        		restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "resident1", "resident1"), Object.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/admins/" twice with the same UserRegisterDTO parameter
	 * Expected: error message is returned, HTTP Status 409 CONFLICT
	 */
	@Test
	public void registerDuplicate() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
		ResponseEntity<Long> responseEntityValidRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), Long.class);

		Long adminId = responseEntityValidRequest.getBody();
		assertEquals(HttpStatus.CREATED, responseEntityValidRequest.getStatusCode());
		
		ResponseEntity<String> responseEntityDuplicateRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		assertEquals(HttpStatus.CONFLICT, responseEntityDuplicateRequest.getStatusCode());
		assertEquals("Admin with username: USERNAME already exists.", responseEntityDuplicateRequest.getBody());
		
		userRepository.delete(adminId);
    }
	
	/**
	 * POST request to "/api/admins/" with invalid UserRegisterDTO - username missing
	 * Expected: error message is returned, HTTP Status UNPROCESSABLE_ENTITY 422
	 */
	@Test
	public void registerUsernameMissing() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO(null, "PASSWORD", "test@gmail.com", new ArrayList<String>());
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'username' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/admins/" with invalid UserRegisterDTO parameter - username too short
	 * Expected: error message is returned, HTTP Status UNPROCESSABLE_ENTITY 422
	 */
	@Test
	public void registerUsernameInvalid() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("123", "PASSWORD", "test@gmail.com", new ArrayList<String>());
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'username' must be at least 4 characters long", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/admins/" with invalid UserRegisterDTO parameter - password missing
	 * Expected: error message is returned, HTTP Status UNPROCESSABLE_ENTITY 422
	 */
	@Test
	public void registerPasswordMissing() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", null, "test@gmail.com", new ArrayList<String>());
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'password' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/admins/" with invalid UserRegisterDTO parameter - password too short
	 * Expected: error message is returned, HTTP Status UNPROCESSABLE_ENTITY 422
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "123", "test@gmail.com", new ArrayList<String>());
		UserRegisterDTO requestObject = new UserRegisterDTO(userRegisterDTO);
		requestObject.setPassword("123");
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'password' must be at least 6 characters long", responseEntity.getBody());
    }

	/**
	 * POST request to "/api/admins/" with invalid UserRegisterDTO parameter - email of invalid format
	 * Expected: error message is returned, HTTP Status 422
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "INVALID_EMAIL_FORMAT", new ArrayList<String>());
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'email' must be of valid format", responseEntity.getBody());
    }
	
}
	