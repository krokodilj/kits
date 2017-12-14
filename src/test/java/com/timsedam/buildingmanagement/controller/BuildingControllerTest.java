package com.timsedam.buildingmanagement.controller;


import com.timsedam.buildingmanagement.dto.BuildingDTO;
import com.timsedam.buildingmanagement.dto.CreateBuildingDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.repository.ResidenceRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;
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
public class BuildingControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResidenceRepository residenceRepository;

    private static final String URL_PREFIX="/api/buildings/";
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
     * Expects: BuildingDTO and HttpStatus.CREATED
     */
    @Test
    public void createBuilding(){
        CreateBuildingDTO createBuildingDTO = new CreateBuildingDTO(
                "sabac","ps541","macva",123,"zelenazgrada"
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createBuildingDTO,"admin","admin"),
                BuildingDTO.class
        );

        BuildingDTO buildingDTO = (BuildingDTO) responseEntity.getBody();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(buildingDTO.getCity(),createBuildingDTO.getCity());
        assertEquals(buildingDTO.getAddress(),createBuildingDTO.getAddress());
        assertEquals(buildingDTO.getCountry(),createBuildingDTO.getCountry());
        assertEquals(buildingDTO.getApartmentCount(),createBuildingDTO.getApartmentCount());
        assertEquals(buildingDTO.getDescription(),createBuildingDTO.getDescription());
    }

    /**
     * POST request to '/api/buildings/ sent by unauthorized user
     * Expects: HttpStatus.FORBIDDEN
     */
    @Test
    public void createBuildingUnauthorized(){
        CreateBuildingDTO createBuildingDTO = new CreateBuildingDTO(
                "sabac","ps541","macva",123,"zelenazgrada"
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createBuildingDTO,"mladen","mladen"),
                Object.class
        );

        assertEquals(HttpStatus.FORBIDDEN,responseEntity.getStatusCode());
    }

    /**
     * POST request to '/api/buildings/' with invalid CreateBuildingDTO
     * Expected: HttpStatus.UNPROCESSABLE_ENTITY
     */
    @Test
    public void createBuildingDTOInvalid(){
        CreateBuildingDTO createBuildingDTO = new CreateBuildingDTO(
                "sad","ps541","macva",123,"zelenazgrada"
        );
        createBuildingDTO.setAddress(null);
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createBuildingDTO,"admin","admin"),
                String.class
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,responseEntity.getStatusCode());
    }

    /**
     * GET request to '/api/buildings/all'
     * Expected: List<BuildingDTO> and HttpStatus.OK
     */
    @Test
    public void getAllBuildings(){
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+"all",
                HttpMethod.GET,
                getRequestEntity(null,"admin","admin"),
                List.class
        );

        List<BuildingDTO> buildingDTOS =(List<BuildingDTO>) responseEntity.getBody();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());

    }

    /**
     * GET request to '/api/buildings/all'
     * Expected: BuildingDTO and HttpStatus.OK
     */
    @Test
    public void getBuildingById(){
        String id="4";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+"all",
                HttpMethod.GET,
                getRequestEntity(null,"admin","admin"),
                List.class
        );

        List<BuildingDTO> buildingDTOS =(List<BuildingDTO>) responseEntity.getBody();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    /**
     * GET request to '/api/buildings/all'
     * Expected: message and HttpStatus.NOT_FOUND
     */
    @Test
    public void getBuildingByIdInvalid(){
        String id="9999";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+id,
                HttpMethod.GET,
                getRequestEntity(null,"admin","admin"),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("Building does not exists",responseEntity.getBody());
    }

}
