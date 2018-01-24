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

import com.timsedam.buildingmanagement.dto.request.CompanyCreateDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyControllerTest {
	
	private static final String URL = "/api/companies/";
	
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
	 * POST request to "/api/companies/" with valid CompanyRegisterDTO parameter
	 * Expected: new Comapny's id is returned to the client, HTTP Status CREATED 201
	 */
	@Test
	public void registerCompany() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), Long.class);
			
		Long companyId = responseEntity.getBody();
		Company company = (Company)userRepository.findOne(companyId);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(companyCreateDTO.getUsername(), company.getUsername());
		assertEquals(companyCreateDTO.getPassword(), company.getPassword());
		assertEquals(companyCreateDTO.getEmail(), company.getEmail());
		assertEquals(companyCreateDTO.getName(), company.getName());
		assertEquals(companyCreateDTO.getLocation(), company.getLocation());
		assertEquals(companyCreateDTO.getPIB(), company.getPIB());
		assertEquals(companyCreateDTO.getPhoneNumber(), company.getPhoneNumber());
		
		userRepository.delete(company.getId());
    }
	
	/**
     * POST request to "/api/companies/" sent by unauthorised user
     * Expected: HttpStatus FORBIDDEN 403
     */
    @Test
    public void registerCompanyUnauthorised() {
    	CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");

        ResponseEntity<?> responseEntity = 
        		restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "resident1", "resident1"), Object.class);

        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/companies/" twice with the same CompanyRegisterDTO parameter
	 * Expected: error message is returned, HTTP Status CONFLICT 409
	 */
	@Test
	public void registerDuplicate() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");
		ResponseEntity<Long> responseEntityValidRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), Long.class);

		Long companyId = responseEntityValidRequest.getBody();
		assertEquals(HttpStatus.CREATED, responseEntityValidRequest.getStatusCode());
		
		ResponseEntity<String> responseEntityDuplicateRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		assertEquals(HttpStatus.CONFLICT, responseEntityDuplicateRequest.getStatusCode());
		assertEquals("Company with username: USERNAME already exists.", responseEntityDuplicateRequest.getBody());
		
		userRepository.delete(companyId);
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO - username missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerUsernameMissing() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO(null, "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'username' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - username too short
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerUsernameInvalid() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("123", "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'username' must be at least 4 characters long", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - password missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerPasswordMissing() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", null, "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'password' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - password too short
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "123", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'password' must be at least 6 characters long", responseEntity.getBody());
    }

	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - email of invalid format
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "PASSWORD", "INVALID_EMAIL_FORMAT", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", "PHONE");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'email' must be of valid format", responseEntity.getBody());
    }

	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - name missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerNameMissing() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						null, "LOCATION", "PIB", "PHONE");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'name' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - PIB missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerPIBMissing() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", null, "PHONE");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'PIB' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/companies/" with invalid CompanyRegisterDTO parameter - phoneNumber missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerPhoneInvalid() throws Exception {
		CompanyCreateDTO companyCreateDTO = 
				new CompanyCreateDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>(),
						"NAME", "LOCATION", "PIB", null);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(companyCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'phoneNumber' not provided", responseEntity.getBody());
    }
	

}
