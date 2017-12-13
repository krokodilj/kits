package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

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

import com.timsedam.buildingmanagement.dto.MeetingCreateDTO;
import com.timsedam.buildingmanagement.dto.MeetingDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.repository.MeetingRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MeetingControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	private static final String URL_PREFIX = "/api/meetings/";
	
	private MeetingCreateDTO validMeetingCreateDTO = 
			new MeetingCreateDTO(LocalDateTime.now().plusDays(1), 2L, "Stan broj 19.");
	
	private String getManagerToken() {
		UserLoginDTO userLoginData = new UserLoginDTO("manager", "manager");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity("/api/auth/login", userLoginData, String.class);
		return responseEntity.getBody();
	}
	
	private HttpEntity<Object> getRequestEntity(Object params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", getManagerToken());
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
		return requestEntity;
	}
	
	/**
	 * POST request to "/api/meetings/" with valid MeetingCreate parameter
	 * Expected: new MeetingDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void createMeeting() throws Exception {
		
		ResponseEntity<MeetingDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validMeetingCreateDTO), MeetingDTO.class);
		
		MeetingDTO meetingFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validMeetingCreateDTO.getStartsAt(), meetingFromResponse.getStartsAt());
		assertEquals(validMeetingCreateDTO.getLocation(), meetingFromResponse.getLocation());

		meetingRepository.delete(meetingFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid MeetingCreate parameter - request sender is not the Building manager 
	 * Expected: no MeetingDTO is returned to the client, HTTP Status 400
	 */
	@Test
	public void userNotManagerCreateMeeting() throws Exception {
		
		MeetingCreateDTO invalidMeetingCreateDTO = new MeetingCreateDTO(validMeetingCreateDTO);
		invalidMeetingCreateDTO.setBuildingId(1L);
		
		ResponseEntity<MeetingDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidMeetingCreateDTO), MeetingDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid MeetingCreate parameter - request parameter is in the past 
	 * Expected: no MeetingDTO is returned to the client, HTTP Status 400
	 */
	@Test
	public void startsAtInPastCreateMeeting() throws Exception {
		
		MeetingCreateDTO invalidMeetingCreateDTO = new MeetingCreateDTO(validMeetingCreateDTO);
		invalidMeetingCreateDTO.setStartsAt(LocalDateTime.now().minusDays(1));
		
		ResponseEntity<MeetingDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidMeetingCreateDTO), MeetingDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid MeetingCreate parameter - missing startsAt parameter 
	 * Expected: no MeetingDTO is returned to the client, HTTP Status 400
	 */
	@Test
	public void nullLocationCreateMeeting() throws Exception {
		
		MeetingCreateDTO invalidMeetingCreateDTO = new MeetingCreateDTO(validMeetingCreateDTO);
		invalidMeetingCreateDTO.setStartsAt(null);
		
		ResponseEntity<MeetingDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidMeetingCreateDTO), MeetingDTO.class);
		
		MeetingDTO meetingFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid MeetingCreate parameter - missing buildingId parameter 
	 * Expected: no MeetingDTO is returned to the client, HTTP Status 400
	 */
	@Test
	public void nullBuildingIdCreateMeeting() throws Exception {
		
		MeetingCreateDTO invalidMeetingCreateDTO = new MeetingCreateDTO(validMeetingCreateDTO);
		invalidMeetingCreateDTO.setBuildingId(null);
		
		ResponseEntity<MeetingDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidMeetingCreateDTO), MeetingDTO.class);
		
		MeetingDTO meetingFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }

}
