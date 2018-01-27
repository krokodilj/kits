package com.timsedam.buildingmanagement.controller;


import static org.junit.Assert.assertEquals;

import java.util.List;

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

import com.timsedam.buildingmanagement.dto.request.BuildingCreateDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.response.BuildingDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.repository.BuildingRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildingControllerTest {
	
	private static final String URL_PREFIX = "/api/buildings/";

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private BuildingRepository buildingRepository;

    private String getUserToken(String username, String password) {
        UserLoginDTO userLoginData = new UserLoginDTO(username, password);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login", userLoginData,
                String.class);
        return responseEntity.getBody();
    }

    private HttpEntity<Object> getRequestEntity(Object params, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", getUserToken(username, password));

        HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
        return requestEntity;
    }

    /**
     * POST request to '/api/buildings/ with valid CreateBuildingDTO
     * Expected: BuildingDTO, HTTP Status 201 CREATED
     */
    @Test
    public void createBuilding(){
        BuildingCreateDTO buildingCreateDTO = 
        	new BuildingCreateDTO("sabac", "ps541", "serbia", 123, "zelenazgrada",11);
        
        ResponseEntity<Long> responseEntity = 
        	restTemplate.postForEntity(
                URL_PREFIX,
                getRequestEntity(buildingCreateDTO, "admin1", "admin1"),
                Long.class
        	);

        Long id = responseEntity.getBody();
        Building building = buildingRepository.findOne(id);
        
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(building.getCity(), buildingCreateDTO.getCity());
        assertEquals(building.getAddress(), buildingCreateDTO.getAddress());
        assertEquals(building.getCountry(), buildingCreateDTO.getCountry());
        assertEquals(building.getApartmentCount(), buildingCreateDTO.getApartmentCount());
        assertEquals(building.getDescription(), buildingCreateDTO.getDescription());
        
        buildingRepository.delete(id);
    }

    /**
     * POST request to '/api/buildings/ sent by unauthorized user
     * Expected: HTTP Status 403 FORBIDDEN
     */
    @Test
    public void createBuildingUnauthorized(){
        BuildingCreateDTO buildingCreateDTO = 
        	new BuildingCreateDTO("sabac", "ps541", "macva", 123, "zelenazgrada",11);
        
        ResponseEntity responseEntity = 
        	restTemplate.postForEntity(
                URL_PREFIX,
                getRequestEntity(buildingCreateDTO, "resident1", "resident1"),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    
	/**
	 * POST request to "/api/buildings/" twice with the same CreateBuildingDTO parameter
	 * Expected: error message is returned, HTTP Status 409 CONFLICT
	 */
	@Test
	public void registerDuplicate() throws Exception {
        BuildingCreateDTO buildingCreateDTO = 
            	new BuildingCreateDTO("sabac", "ps541", "macva", 123, "zelenazgrada",11);
		
		ResponseEntity<Long> responseEntityValidRequest = 
			restTemplate.postForEntity(
				URL_PREFIX, 
				getRequestEntity(buildingCreateDTO, "admin1", "admin1"), 
				Long.class);

		Long buildingId = responseEntityValidRequest.getBody();
		assertEquals(HttpStatus.CREATED, responseEntityValidRequest.getStatusCode());
		
		ResponseEntity<String> responseEntityDuplicateRequest = 
			restTemplate.postForEntity(
				URL_PREFIX, 
				getRequestEntity(buildingCreateDTO, "admin1", "admin1"), 
				String.class);
		
		assertEquals(HttpStatus.CONFLICT, responseEntityDuplicateRequest.getStatusCode());
		assertEquals("Building with address: ps541, city: sabac, country: macva already exists.",
				responseEntityDuplicateRequest.getBody());
		buildingRepository.delete(buildingId);
    }
	
	/**
	 * POST request to '/api/buildings/' with invalid CreateBuildingDTO - city missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerUsernameMissing() throws Exception {
        BuildingCreateDTO buildingCreateDTO = 
        	new BuildingCreateDTO(null, "ps541", "macva", 123, "zelenazgrada",11);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(buildingCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'city' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to '/api/buildings/' with invalid CreateBuildingDTO - address missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerAddressMissing() throws Exception {
        BuildingCreateDTO buildingCreateDTO = 
        	new BuildingCreateDTO("sabac", null, "macva", 123, "zelenazgrada",11);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(buildingCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'address' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to '/api/buildings/' with invalid CreateBuildingDTO - state missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerContryMissing() throws Exception {
        BuildingCreateDTO buildingCreateDTO = 
        	new BuildingCreateDTO("sabac", "ps541", null, 123, "zelenazgrada",11);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(buildingCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'country' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to '/api/buildings/' with invalid CreateBuildingDTO - apartmentCount negative number
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerApartmentCountNegative() throws Exception {
        BuildingCreateDTO buildingCreateDTO = 
        	new BuildingCreateDTO("sabac", "ps541", "macva", -1, "zelenazgrada",11);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(buildingCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'apartmentCount' cannot be a negative value", responseEntity.getBody());
    }
	
	/**
	 * POST request to '/api/buildings/' with invalid CreateBuildingDTO - description missing
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void registerDescriptionMissing() throws Exception {
        BuildingCreateDTO buildingCreateDTO = 
        	new BuildingCreateDTO("sabac", "ps541", "macva", 123, null,11);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(buildingCreateDTO, "admin1", "admin1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'description' not provided", responseEntity.getBody());
    }

    /**
     * GET request to '/api/buildings/all'
     * Expected: List<BuildingDTO>, HTTP Status OK 200
     */
    @Test
    public void getAllBuildings(){
    	
    	long numberOfBuildings = buildingRepository.count();
    	
        ResponseEntity<List> responseEntity = 
        	restTemplate.exchange(URL_PREFIX, HttpMethod.GET, getRequestEntity(null, "admin1", "admin1"), List.class);
        
        List<BuildingDTO> buildingDTOS = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(numberOfBuildings, buildingDTOS.size());
    }
    
    /**
     * GET request to '/api/buildings/1'
     * Expected: List<BuildingDTO>, HTTP Status OK 200
     */
    @Test
    public void getBuildingById(){
    	String id = "1";
        ResponseEntity<BuildingDTO> responseEntity = 
        	restTemplate.exchange(URL_PREFIX + "/" + id, HttpMethod.GET, getRequestEntity(null, "admin1", "admin1"), BuildingDTO.class);

        BuildingDTO building = responseEntity.getBody();
        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(building.getId(), Long.parseLong(id));
    }


    /**
     * GET request to '/api/buildings/9999'
     * Expected: error message is returned, HTTP Status 404 NOT_FOUND
     */
    @Test
    public void getBuildingByIdInvalid(){
        String id = "9999";
        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(URL_PREFIX + id, HttpMethod.GET, getRequestEntity(null, "admin1", "admin1"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Building with id: 9999 doesnt exist.", responseEntity.getBody());
    }

}
