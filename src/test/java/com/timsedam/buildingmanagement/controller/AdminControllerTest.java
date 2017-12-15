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

import com.timsedam.buildingmanagement.dto.AdminDTO;
import com.timsedam.buildingmanagement.dto.AdminRegisterDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class AdminControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String URL = "/api/admins/";
		
	private AdminRegisterDTO validAdminRegisterDTO = new AdminRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
	
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
	 * POST request to "/api/admins/" with valid AdminRegisterDTO parameter
	 * Expected: new AdminDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerAdmin() throws Exception {
		
		ResponseEntity<AdminDTO> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(validAdminRegisterDTO), AdminDTO.class);
		
		AdminDTO adminFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validAdminRegisterDTO.getUsername(), adminFromResponse.getUsername());
		assertEquals(validAdminRegisterDTO.getEmail(), adminFromResponse.getEmail());
		assertEquals(validAdminRegisterDTO.getPictures(), adminFromResponse.getPictures());
		
		userRepository.delete(adminFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/admins/" twice with the same AdminRegisterDTO parameter
	 * Expected: new AdminDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerDuplicate() throws Exception {
		
		ResponseEntity<AdminDTO> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(validAdminRegisterDTO), AdminDTO.class);
	
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		AdminDTO adminFromResponse = responseEntity.getBody();
		
		responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(validAdminRegisterDTO), AdminDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		userRepository.delete(adminFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/admins/" with invalid AdminRegisterDTO parameter - username too short
	 * Expected: no AdminDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerUsernameInvalid() throws Exception {
		
		AdminRegisterDTO requestObject = new AdminRegisterDTO(validAdminRegisterDTO);
		requestObject.setUsername("123");
		
		ResponseEntity<AdminDTO> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject), AdminDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
		
    }
	
	/**
	 * POST request to "/api/admins/" with invalid AdminRegisterDTO parameter - password too short
	 * Expected: no AdminDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		
		AdminRegisterDTO requestObject = new AdminRegisterDTO(validAdminRegisterDTO);
		requestObject.setPassword("123");
		
		ResponseEntity<AdminDTO> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject), AdminDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/admins/" with invalid AdminRegisterDTO parameter - email of invalid format
	 * Expected: no AdminDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		AdminRegisterDTO requestObject = new AdminRegisterDTO(validAdminRegisterDTO);
		requestObject.setEmail("INVALID_EMAIL_FORMAT");
		
		ResponseEntity<AdminDTO> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(requestObject), AdminDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
}
	