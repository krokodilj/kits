package com.timsedam.buildingmanagement.controller;


import com.timsedam.buildingmanagement.dto.*;
import com.timsedam.buildingmanagement.repository.ResidenceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResidenceControllerTest {

    @Autowired
    private  ResidenceRepository residenceRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL_PREFIX="/api/residences/";
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
     * POST request to '/api/residences/' with valid CreateResidentDTO
     * Expected: ResidenceDTO and HttpStatus.CREATED

    @Test
    public void createResidence(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(
                1,4,99
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO,"admin","admin"),
                ResidenceDTO.class
        );
        ResidenceDTO residence = (ResidenceDTO) responseEntity.getBody();
        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(createResidenceDTO.getApartmentNumber(),residence.getApartmentNumber());
        assertEquals(createResidenceDTO.getFloorNumber(),residence.getFloorNumber());
        System.out.println(residence.toString());
        residenceRepository.delete(residence.getId());
    }
    */

    /**
     * POST request to '/api/residences/' with invalid building in createResidenceDTO
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void createResidenceInvalidBuilding(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(
                999,4,4
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO,"admin","admin"),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("Building not found",responseEntity.getBody());

    }

    /**
     * POST request to '/api/residences/'sent by unauthorized user
     * Expected: HttpStatus.FORBIDDEN
     */
    @Test
    public void createResidenceUnauthorized(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(
                1,4,4
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO,"mladen","mladen"),
                Object.class
        );

        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());

    }

    /**
     * POST request to '/api/residences/' where apartment number is taken
     * Expected: error message and HttpStatus.CONFLICT
     */
    @Test
    public void createResidenceApartmentNumTaken(){
        CreateResidenceDTO createResidenceDTO = new CreateResidenceDTO(
                1,4,2
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createResidenceDTO,"admin","admin"),
                String.class
        );

        assertEquals(HttpStatus.CONFLICT,responseEntity.getStatusCode());
        assertEquals("Apartment number taken",responseEntity.getBody());

    }

    /**
     * GET request to '/api/residences/{residenceId}'
     * Expected: ResidentDTO and HttpStatus.OK
     */
    @Test
    public void getResidenceById(){
        String residenceId = "1";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+residenceId,
                HttpMethod.GET,
                getRequestEntity(null,"admin","admin"),
                ResidenceDTO.class
        );

        ResidenceDTO residence = (ResidenceDTO) responseEntity.getBody();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(Long.parseLong(residenceId),residence.getId());
    }

    /**
     * GET request to '/api/residences/{residenceId}'
     * Expected: message and HttpStatus.NOT_FOUND
     */
    @Test
    public void getResidenceByIdNotfound(){
        String residenceId = "999";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+residenceId,
                HttpMethod.GET,
                getRequestEntity(null,"admin","admin"),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("Residence not found",responseEntity.getBody());
    }

    /**
     * GET request to '/api/residences/byBuilding/{buildingId}'
     * Expected: List<ResidenceDTO> and HttpStatus.OK
     */
    @Test
    public void getResidencesByBuilding(){
        String buildingId = "2";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+"/by_building/"+buildingId,
                HttpMethod.GET,
                getRequestEntity(null,"admin","admin"),
                List.class
        );

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    /**
     * GET request to '/api/residences/byBuilding/{buildingId}' with invalid buildingId
     * Expected: message and HttpStatus.NOT_FOUND
     */
    @Test
    public void getResidencesByBuildingNotfound(){
        String buildingId = "999";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+"/by_building/"+buildingId,
                HttpMethod.GET,
                getRequestEntity(null,"admin","admin"),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("Building not found",responseEntity.getBody());
    }


}
