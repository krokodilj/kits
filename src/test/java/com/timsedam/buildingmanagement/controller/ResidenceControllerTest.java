package com.timsedam.buildingmanagement.controller;


import static org.junit.Assert.assertEquals;

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

import com.timsedam.buildingmanagement.dto.request.CreateResidenceDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.response.ResidenceDTO;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.repository.ResidenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResidenceControllerTest {

    @Autowired
    private  ResidenceRepository residenceRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL_PREFIX = "/api/residences/";
    
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
     * POST request to '/api/residences/' with valid CreateResidentDTO
     * Expected: ResidenceDTO and HttpStatus.CREATED
	*/
    @Test
    public void createResidence(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(1, 4, 99);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO, "admin1", "admin1"),
                Long.class);
        
        Long residenceId = responseEntity.getBody();
        Residence residence = residenceRepository.findOne(residenceId);
        
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(createResidenceDTO.getApartmentNumber(), residence.getApartmentNumber());
        assertEquals(createResidenceDTO.getFloorNumber(), residence.getFloorNumber());
        
        residenceRepository.delete(residence.getId());
    }
    
    /**
     * POST request to '/api/residences/' with invalid CreateResidentDTO - taken apartmentNumber in the Building
     * Expected: error message and HttpStatus.CREATED
	*/
    @Test
    public void createResidenceTakenLocation(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(1, 4, 99);
        ResponseEntity<Long> responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO, "admin1", "admin1"),
                Long.class);
        
        Long residenceId = responseEntity.getBody();
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        
        ResponseEntity<String> responseEntityDuplicate = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO, "admin1", "admin1"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntityDuplicate.getStatusCode());
        assertEquals("Residence with apartmentNumber: 99 already exists in Building with id: 1", responseEntityDuplicate.getBody());
        
        residenceRepository.delete(residenceId);
    }
    
    /**
     * POST request to '/api/residences/'sent by unauthorized user
     * Expected: HttpStatus.FORBIDDEN
     */
    @Test
    public void createResidenceUnauthorized(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(1, 4, 4);
        ResponseEntity<Object> responseEntity = 
        	restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO, "resident1", "resident1"),
                Object.class);

        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
    }

    /**
     * POST request to '/api/residences/' with invalid building in createResidenceDTO
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void createResidenceInvalidBuilding(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(999, 4, 4);
        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO, "admin1", "admin1"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Building with id: 999 doesn't exist.", responseEntity.getBody());
    }

    /**
     * POST request to '/api/residences/' where apartment number is taken
     * Expected: error message and HttpStatus.CONFLICT
     */
    @Test
    public void createResidenceApartmentNumTaken(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(1, 1, 1);
        
        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
        		URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO, "admin1", "admin1"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Residence with apartmentNumber: 1 already exists in Building with id: 1", responseEntity.getBody());
    }

    /**
     * GET request to '/api/residences/{residenceId}'
     * Expected: ResidentDTO and HttpStatus.OK
     */
    @Test
    public void getResidenceById() {
        String residenceId = "1";
        ResponseEntity<ResidenceDTO> responseEntity = 
        	restTemplate.exchange(
                URL_PREFIX + residenceId,
                HttpMethod.GET,
                getRequestEntity(null, "admin1", "admin1"),
                ResidenceDTO.class);

        ResidenceDTO residence = responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Long.parseLong(residenceId), residence.getId());
    }

    /**
     * GET request to '/api/residences/{residenceId}' - invalid residenceId
     * Expected: message and HttpStatus.NOT_FOUND
     */
    @Test
    public void getResidenceByIdNotfound(){
        String residenceId = "999";
        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(
                URL_PREFIX + residenceId,
                HttpMethod.GET,
                getRequestEntity(null, "admin1", "admin1"),
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Residence with id: 999 doesn't exist.", responseEntity.getBody());
    }

}
