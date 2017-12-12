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

import com.timsedam.buildingmanagement.dto.CompanyDTO;
import com.timsedam.buildingmanagement.dto.CompanyRegisterDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final String URL_PREFIX = "/api/companies/";
	
	private CompanyRegisterDTO validCompanyRegisterDTO = 
			new CompanyRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com",
					"picture.png", "NAME", "LOCATION", "PIB", "PHONE");
	
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
	 * POST request to "/api/companies/" with valid CompanyRegisterDTO parameter
	 * Expected: new CompanyDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerCompany() throws Exception {
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validCompanyRegisterDTO), CompanyDTO.class);
		
		CompanyDTO companyFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validCompanyRegisterDTO.getUsername(), companyFromResponse.getUsername());
		assertEquals(validCompanyRegisterDTO.getEmail(), companyFromResponse.getEmail());
		assertEquals(validCompanyRegisterDTO.getPicture(), companyFromResponse.getPicture());
		
		
		userRepository.delete(companyFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/companies/" twice with the same CompanyRegisterDTO parameter
	 * Expected: no CompanyDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void registerDuplicate() throws Exception {
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validCompanyRegisterDTO), CompanyDTO.class);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		CompanyDTO adminFromResponse = responseEntity.getBody();
		
		responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validCompanyRegisterDTO), CompanyDTO.class);
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		
		userRepository.delete(adminFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - username too short
	 * Expected: no CompanyDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerUsernameInvalid() throws Exception {
		
		CompanyRegisterDTO requestObject = new CompanyRegisterDTO(validCompanyRegisterDTO);
		requestObject.setUsername("123");
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), CompanyDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
		
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - password too short
	 * Expected: no CompanyDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		
		CompanyRegisterDTO requestObject = new CompanyRegisterDTO(validCompanyRegisterDTO);
		requestObject.setPassword("123");
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), CompanyDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - email parameter format invalid
	 * Expected: no CompanyDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		
		CompanyRegisterDTO requestObject = new CompanyRegisterDTO(validCompanyRegisterDTO);
		requestObject.setEmail("INVALID EMAIL");
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), CompanyDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - name parameter null
	 * Expected: no CompanyDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerNameInvalid() throws Exception {
		
		CompanyRegisterDTO requestObject = new CompanyRegisterDTO(validCompanyRegisterDTO);
		requestObject.setName(null);
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), CompanyDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - PIB parameter null
	 * Expected: no CompanyDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerPIBInvalid() throws Exception {
		
		CompanyRegisterDTO requestObject = new CompanyRegisterDTO(validCompanyRegisterDTO);
		requestObject.setPIB(null);
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), CompanyDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - phone number parameter null
	 * Expected: no CompanyDTO is returned, HTTP Status 422
	 */
	@Test
	public void registerPhoneInvalid() throws Exception {
		
		CompanyRegisterDTO requestObject = new CompanyRegisterDTO(validCompanyRegisterDTO);
		requestObject.setPhoneNumber(null);
		
		ResponseEntity<CompanyDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(requestObject), CompanyDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody(), null);
    }
	
	

}
