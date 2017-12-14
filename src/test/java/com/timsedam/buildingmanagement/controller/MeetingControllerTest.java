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
import org.springframework.http.HttpMethod;
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
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/meetings/{meetingId}" with valid meetingId parameter 
	 * Expected: MeetingDTO is returned to the client, HTTP Status 200
	 */
	@Test
	public void getMeeting() throws Exception {
		
		ResponseEntity<MeetingDTO> responseEntity = restTemplate.exchange(
				"/api/meetings/1", HttpMethod.GET, getRequestEntity(null), MeetingDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/meetings/{meetingId}" with invalid meetingId parameter - Meeting with specified id doesnt exist 
	 * Expected: MeetingDTO is returned to the client, HTTP Status 200
	 */
	@Test
	public void invalidMeetingIdGetMeeting() throws Exception {
		
		ResponseEntity<MeetingDTO> responseEntity = restTemplate.exchange(
				"/api/meetings/156", HttpMethod.GET, getRequestEntity(null), MeetingDTO.class);
		
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/meetings?buildingId=6"
	 * Expected: MeetingDTO array is returned to the client, HTTP Status 200
	 */
	@Test
	public void getMeetingsByBuildingId() throws Exception {
		
		ResponseEntity<MeetingDTO[]> responseEntity = restTemplate.exchange(
				"/api/meetings?buildingId=6", HttpMethod.GET, getRequestEntity(null), MeetingDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
    }
	
	/**
	 * GET request to "/api/meetings?buildingId=123" - invalid buildingId parameter
	 * Expected: empty MeetingDTO array is returned to the client, HTTP Status 200
	 */
	@Test
	public void getMeetingsByBuildingIdInvalidBuildId() throws Exception {
		
		ResponseEntity<MeetingDTO[]> responseEntity = restTemplate.exchange(
				"/api/meetings?buildingId=123", HttpMethod.GET, getRequestEntity(null), MeetingDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody().length, 0);
		
    }

}
