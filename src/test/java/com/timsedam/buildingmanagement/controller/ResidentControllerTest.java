package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.request.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.ResidenceRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResidentControllerTest {
	
	private static final String URL = "/api/residents/";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ResidenceRepository residenceRepository;

    private String getUserToken(String username, String password) {
        UserLoginDTO userLoginData = new UserLoginDTO(username, password);
        ResponseEntity<String> responseEntity = 
        		restTemplate.postForEntity("/api/auth/login", userLoginData, String.class);
        return responseEntity.getBody();
    }

    private HttpEntity<Object> getRequestEntity(Object params, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", getUserToken(username, password));

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
        return requestEntity;
    }

    /**
     * POST request to '/api/residents' with valid UserRegisterDTO
     * Expected:  new REsident's id is returned and HTTP Status CREATED 201
     */
    @Test
    public void registerResident() {
    	UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), Long.class);
        Long id = responseEntity.getBody();
        User user = userRepository.findOne(id);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(user.getUsername(), userRegisterDTO.getUsername());
        assertEquals(user.getPassword(), userRegisterDTO.getPassword());
        assertEquals(user.getEmail(), userRegisterDTO.getEmail());

        userRepository.delete(id);
    }

    /**
     * POST request to '/api/residents/' sent by unauthorised user
     * Expected: HTTP Status FORBIDDEN 403
     */
    @Test
    public void registerResidentUnauthorised(){
    	UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
        ResponseEntity<?> responseEntity = 
        		restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "resident1", "resident1"), Object.class);
        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
    }
    
	/**
	 * POST request to "/api/admins/" twice with the same UserRegisterDTO parameter
	 * Expected: error message is returned, HTTP Status NOT_FOUND 404
	 */
	@Test
	public void registerDuplicate() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "test@gmail.com", new ArrayList<String>());
		ResponseEntity<Long> responseEntityValidRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), Long.class);

		Long residentId = responseEntityValidRequest.getBody();
		assertEquals(HttpStatus.CREATED, responseEntityValidRequest.getStatusCode());
		
		ResponseEntity<String> responseEntityDuplicateRequest = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		assertEquals(HttpStatus.NOT_FOUND, responseEntityDuplicateRequest.getStatusCode());
		assertEquals("Resident with username: USERNAME already exists.", responseEntityDuplicateRequest.getBody());
		
		userRepository.delete(residentId);
    }
	
	/**
	 * POST request to "/api/residents/" with invalid UserRegisterDTO - username missing
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
	 * POST request to "/api/residents/" with invalid UserRegisterDTO parameter - username too short
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
	 * POST request to "/api/residents/" with invalid UserRegisterDTO parameter - password missing
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
	 * POST request to "/api/residents/" with invalid UserRegisterDTO parameter - password too short
	 * Expected: error message is returned, HTTP Status UNPROCESSABLE_ENTITY 422
	 */
	@Test
	public void registerPasswordInvalid() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "123", "test@gmail.com", new ArrayList<String>());
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'password' must be at least 6 characters long", responseEntity.getBody());
    }

	/**
	 * POST request to "/api/residents/" with invalid UserRegisterDTO parameter - email of invalid format
	 * Expected: error message is returned, HTTP Status UNPROCESSABLE_ENTITY 422
	 */
	@Test
	public void registerEmailInvalid() throws Exception {
		UserRegisterDTO userRegisterDTO = new UserRegisterDTO("USERNAME", "PASSWORD", "INVALID_EMAIL_FORMAT", new ArrayList<String>());
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(userRegisterDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'email' must be of valid format", responseEntity.getBody());
	}
    
    /**
     * PUT request to 'api/residents/{residentId}/add_to_residence/{residenceId} with valid path params
     * Expected: HTTP Status 200 OK
     */
    @Test
    public void setResidence(){
        String residentId = "1";
        String residenceId = "3";
        
        ResponseEntity<Object> responseEntity = 
        	restTemplate.exchange(
                URL + residentId + "/add_to_residence/" + residenceId,
                HttpMethod.PUT,
                getRequestEntity(null, "admin1", "admin1"),
                Object.class);
        
        Residence residence = residenceRepository.findOne(Long.parseLong(residenceId));
        
        ArrayList<Long> residentIds = new ArrayList<Long>();
        for(User r : residence.getResidents())
        	residentIds.add(r.getId());
        assert(residentIds.contains(Long.parseLong(residentId)));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_residence/{residenceId}
     *  to non existing resident
     * Expected: error message is returned, HTTP Status NOT_FOUND 404
     */
    @Test
    public void setResidenceWithInvalidResidentId() {
        String residentId = "76567";
        String residenceId = "3";

        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
                URL + residentId + "/add_to_residence/" + residenceId,
                HttpMethod.PUT,
                getRequestEntity(null, "admin1", "admin1"),
                String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User with ID: 76567 doesn't exist.", responseEntity.getBody());
    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_residence/{residenceId} - non existing residence 
     * Expected: error message is returned, HTTP Status NOT_FOUND 404
     */
    @Test
    public void setResidenceWithInvalidResidenceId() {
        String residentId = "5";
        String residenceId = "27343";
        
        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
                URL + residentId + "/add_to_residence/" + residenceId,
                HttpMethod.PUT,
                getRequestEntity(null, "admin1", "admin1"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Residence with ID: 27343 doesn't exist.", responseEntity.getBody());
    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_owner/{residenceId} with valid path params
     * Expected: HttpStatus.OK
     * Expected: HTTP Status OK 200
     */
    @Test
    public void setResidenceOwner(){
        String residentId = "35";
        String residenceId = "20";

        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
                URL + residentId + "/add_to_owner/" + residenceId,
                HttpMethod.PUT,
                getRequestEntity(null, "admin1", "admin1"),
                String.class);

        Residence residence = residenceRepository.findOne(20L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals((Object)Long.parseLong(residentId), residence.getApartmentOwner().getId());
        
        
        User originalOwner = userRepository.findOne(40L);
        residence.setApartmentOwner(originalOwner);
        residenceRepository.save(residence);
    } 	

    /**
     * PUT request to 'api/residents/{residentId}/add_to_owner/{residenceId} - non existing residence
     * Expected: error message is returned, HTTP Status NOT_FOUND 404
     */
    @Test
    public void setResidenceOwnerWithInvalidResidenceId() {
        String residentId = "35";
        String residenceId = "27343";
        
        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
                URL + residentId + "/add_to_owner/" + residenceId,
                HttpMethod.PUT,
                getRequestEntity(null, "admin1", "admin1"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Residence with ID: " + residenceId + " doesn't exist.", responseEntity.getBody());
    }

    /**
     * PUT request to 'api/residents/{residentId}/add_to_owner/{residenceId} - non existing resident 
     * Expected: error message is returned, HTTP Status NOT_FOUND 404
     */
    @Test
    public void setResidencOwnereWithInvalidResidentId() {
        String residentId = "76567";
        String residenceId = "20";

        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
                URL + residentId + "/add_to_owner/" + residenceId,
                HttpMethod.PUT,
                getRequestEntity(null, "admin1", "admin1"),
                String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User with ID: " + residentId + " doesn't exist.", responseEntity.getBody());
    }

}
