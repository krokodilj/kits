package com.timsedam.buildingmanagement.controller;


import com.timsedam.buildingmanagement.dto.AnnouncementDTO;
import com.timsedam.buildingmanagement.dto.CreateAnnouncementDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnnouncementControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL_PREFIX="/api/announcements/";
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
     * POST method to '/api/announcements/' with valid AnnouncementDTO
     * Expected: AnnouncementDTO and HttpStatus.CREATED
     */
    @Test
    public void createAnnouncement(){
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO(
                "deste drugari", 3 , LocalDateTime.now()
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createAnnouncementDTO,"mladen","mladen"),
                AnnouncementDTO.class
        );

        AnnouncementDTO announcement = (AnnouncementDTO) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(announcement.getContent(),createAnnouncementDTO.getContent());
        assertEquals(announcement.getPostedAt(),createAnnouncementDTO.getPostedAt());
    }

    /**
     * POST method to '/api/announcements/' with invvalid AnnouncementDTO
     * Expected: error message and HttpStatus.UNPROCESSABLE_ENTITY
     */
    @Test
    public void createAnnouncemenInvalidDto(){
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO(
                "deste drugari", 3 , null
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createAnnouncementDTO,"mladen","mladen"),
                String.class
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,responseEntity.getStatusCode());
    }

    /**
     * POST method to '/api/announcements/' with invvalid buildinId
     * Expected: message and HttpStatus.NOT_FOUND
     */
    @Test
    public void createAnnouncemenInvalidBuilding(){
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO(
                "deste drugari", 999, LocalDateTime.now()
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createAnnouncementDTO,"mladen","mladen"),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("Building does not exists",responseEntity.getBody());
    }

    /**
     * POST method to '/api/announcements/' with nonexisting user
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void createAnnouncemenNoUser(){
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO(
                "deste drugari", 1 , LocalDateTime.now()
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                new HttpEntity<>(createAnnouncementDTO,null),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals("User does not exists",responseEntity.getBody());
    }

    /**
     * POST method to '/api/announcements/' with invvalid user
     * Expected: message and HttpStatus.CONFLICT
     */
    @Test
    public void createAnnouncemenInvalidUser(){
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO(
                "deste drugari", 1 , LocalDateTime.now()
        );
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX,
                HttpMethod.POST,
                getRequestEntity(createAnnouncementDTO,"ivan","ivan"),
                String.class
        );

        assertEquals(HttpStatus.CONFLICT,responseEntity.getStatusCode());
        assertEquals("User isn't resident of building",responseEntity.getBody());
    }

    /**
     * GET method to '/api/announcements/by_building/{buildingId}'
     * Expected: List<AnnouncementDTO> and HttpStatus.OK
     */
    @Test
    public void getAnnouncementsByBuilding(){
        String buiildingId="3";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+"by_building/"+buiildingId,
                HttpMethod.GET,
                getRequestEntity(null,"mladen","mladen"),
                List.class
        );

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    /**
     * GET method to '/api/announcements/by_building/{buildingId}'
     * Expected: error message and HttpStatus.CONFLICT
     */
    @Test
    public void getAnnouncementsByBuildingForbidden(){
        String buiildingId="3";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+"by_building/"+buiildingId,
                HttpMethod.GET,
                getRequestEntity(null,"ivan","ivan"),
                String.class
        );

        assertEquals(HttpStatus.CONFLICT,responseEntity.getStatusCode());
        assertEquals("User isn't resident of building",responseEntity.getBody());
    }

    /**
     * GET method to '/api/announcements/by_building/{buildingId}'
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void getAnnouncementsByNoBuilding(){
        String buiildingId="999";
        ResponseEntity responseEntity = restTemplate.exchange(
                URL_PREFIX+"by_building/"+buiildingId,
                HttpMethod.GET,
                getRequestEntity(null,"ivan","ivan"),
                String.class
        );

        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }

}
