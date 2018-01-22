package com.timsedam.buildingmanagement.controller;


import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Entity;

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

import com.timsedam.buildingmanagement.dto.request.CreateAnnouncementDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.response.AnnouncementDTO;
import com.timsedam.buildingmanagement.model.Announcement;
import com.timsedam.buildingmanagement.repository.AnnouncementRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnnouncementControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private AnnouncementRepository announcementRepository;

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
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO("deste drugari", 1 , LocalDateTime.now());
        ResponseEntity<Long> responseEntity = restTemplate.exchange(URL_PREFIX,  HttpMethod.POST, 
        		getRequestEntity(createAnnouncementDTO, "resident1", "resident1"), Long.class);

        Long announcementId = responseEntity.getBody();
        Announcement announcement = announcementRepository.findOne(announcementId);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(announcement.getContent(), createAnnouncementDTO.getContent());
        assertEquals(announcement.getPostedAt().truncatedTo(ChronoUnit.MINUTES), 
        		createAnnouncementDTO.getPostedAt().truncatedTo(ChronoUnit.MINUTES));
        
        announcementRepository.delete(announcementId);
    }

    /**
     * POST method to '/api/announcements/' with invalid AnnouncementDTO - missing postedAt
     * Expected: error message and HttpStatus.UNPROCESSABLE_ENTITY
     */
    @Test
    public void createAnnouncemenInvalidDto(){
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO("deste drugari", 1, null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_PREFIX, HttpMethod.POST,
                getRequestEntity(createAnnouncementDTO, "resident1", "resident1"), String.class);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals("'postedAt' not provided", responseEntity.getBody());
    }

    /**
     * POST method to '/api/announcements/' with invalid buildinId - invalid buildingId 
     * Expected: message and HttpStatus.NOT_FOUND
     */
    @Test
    public void createAnnouncemenInvalidBuilding(){
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO("deste drugari", 999, LocalDateTime.now());
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_PREFIX, HttpMethod.POST,
                getRequestEntity(createAnnouncementDTO, "resident1", "resident1"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Building with id: 999 doesn't exist.", responseEntity.getBody());
    }

    /**
     * POST method to '/api/announcements/' with invalid user - user not resident in building
     * Expected: message and HttpStatus.CONFLICT
     */
    @Test
    public void createAnnouncemenInvalidUser() {
        CreateAnnouncementDTO createAnnouncementDTO = new CreateAnnouncementDTO("deste drugari", 5, LocalDateTime.now());
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_PREFIX, HttpMethod.POST,
                getRequestEntity(createAnnouncementDTO, "resident1", "resident1"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User with id: 16 is not a Resident or Owner in Building with id: 5", responseEntity.getBody());
    }

    /**
     * GET method to '/api/announcements/by_building/{buildingId}'
     * Expected: List<AnnouncementDTO> and HttpStatus.OK
     */
    @Test
    public void getAnnouncementsByBuilding(){
        String buildingId = "1";
        ResponseEntity<AnnouncementDTO[]> responseEntity = restTemplate.exchange(URL_PREFIX + "by_building/" + buildingId, 
        		HttpMethod.GET, getRequestEntity(null, "resident1", "resident1"), AnnouncementDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /**
     * GET method to '/api/announcements/by_building/{buildingId}'
     * Expected: error message and HttpStatus.CONFLICT
     */
    @Test
    public void getAnnouncementsByBuildingForbidden(){
        String buiildingId = "3";
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_PREFIX + "by_building/" + buiildingId,
                HttpMethod.GET, getRequestEntity(null, "resident1", "resident1"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("User with id: 16 is not a Resident or Owner in Building with id: 3", responseEntity.getBody());
    }

    /**
     * GET method to '/api/announcements/by_building/{buildingId}'
     * Expected: error message and HttpStatus.NOT_FOUND
     */
    @Test
    public void getAnnouncementsByNoBuilding(){
        String buiildingId = "999";
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL_PREFIX + "by_building/" + buiildingId,
                HttpMethod.GET, getRequestEntity(null, "resident1", "resident1"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Building with id: 999 doesn't exist.", responseEntity.getBody());
    }

}
