package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

import com.timsedam.buildingmanagement.dto.request.CreateMeetingDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.response.MeetingDTO;
import com.timsedam.buildingmanagement.model.Meeting;
import com.timsedam.buildingmanagement.repository.MeetingRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class MeetingControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	private static final String URL_PREFIX = "/api/meetings/";
	
	private CreateMeetingDTO validMeetingCreateDTO = 
			new CreateMeetingDTO(LocalDateTime.now().plusDays(1), 1L, "Stan broj 19.");
	
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
	 * POST request to "/api/meetings/" with valid CreateMeetingDTO
	 * Expected: Meeting's id is returned to the client, HTTP Status 201
	 */
	@Test
	public void createMeeting() throws Exception {
		
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validMeetingCreateDTO, "manager1", "manager1"), Long.class);
		
		Long meetingId = responseEntity.getBody();
		Meeting meeting = meetingRepository.findOne(meetingId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validMeetingCreateDTO.getStartsAt().truncatedTo(ChronoUnit.MINUTES), 
				meeting.getStartsAt().truncatedTo(ChronoUnit.MINUTES));
		assertEquals(validMeetingCreateDTO.getLocation(), meeting.getLocation());

		meetingRepository.delete(meetingId);
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid MeetingCreate parameter - request sender is not the Building Manager 
	 * Expected: error message, HTTP Status 404
	 */
	@Test
	public void userNotManagerCreateMeeting() throws Exception {
		
		CreateMeetingDTO invalidMeetingCreateDTO = new CreateMeetingDTO(validMeetingCreateDTO);
		invalidMeetingCreateDTO.setBuildingId(5L);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidMeetingCreateDTO, "manager1", "manager1"), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("User with id: 11 is not the Manager of Building with id: 5", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid MeetingCreate parameter - request parameter is in the past 
	 * Expected: error message, HTTP Status 404
	 */
	@Test
	public void startsAtInPastCreateMeeting() throws Exception {
		
		CreateMeetingDTO invalidMeetingCreateDTO = new CreateMeetingDTO(validMeetingCreateDTO);
		invalidMeetingCreateDTO.setStartsAt(LocalDateTime.now().minusDays(1));
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidMeetingCreateDTO, "manager1", "manager1"), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Time provided is in the past.", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid MeetingCreate parameter - missing startsAt parameter 
	 * Expected: no MeetingDTO is returned to the client, HTTP Status 400
	 */
	@Test
	public void nullLocationCreateMeeting() throws Exception {
		
		CreateMeetingDTO invalidMeetingCreateDTO = new CreateMeetingDTO(validMeetingCreateDTO);
		invalidMeetingCreateDTO.setStartsAt(null);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidMeetingCreateDTO, "manager1", "manager1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'startTime' not provided", responseEntity.getBody());
    }
	
	/**
	 * GET request to "/api/meetings/{meetingId}" with valid meetingId parameter 
	 * Expected: MeetingDTO is returned to the client, HTTP Status 200
	 */
	@Test
	public void getMeeting() throws Exception {
		
		ResponseEntity<MeetingDTO> responseEntity = 
			restTemplate.exchange("/api/meetings/1", HttpMethod.GET, getRequestEntity(null, "manager1", "manager1"), MeetingDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/meetings/{meetingId}" with invalid meetingId parameter - Meeting with specified id doesnt exist 
	 * Expected: error message, HTTP Status 404
	 */
	@Test
	public void invalidMeetingIdGetMeeting() throws Exception {
		
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange("/api/meetings/156", HttpMethod.GET, getRequestEntity(null, "manager1", "manager1"), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Meeting with id: 156 doesn't exist.", responseEntity.getBody());
    }
	
	/**
	 * GET request to "/api/meetings?buildingId=6"
	 * Expected: MeetingDTO array is returned to the client, HTTP Status 200
	 */
	@Test
	public void getMeetingsByBuildingId() throws Exception {
		
		ResponseEntity<MeetingDTO[]> responseEntity = 
				restTemplate.exchange("/api/meetings?buildingId=6", HttpMethod.GET, getRequestEntity(null, "manager1", "manager1"), MeetingDTO[].class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/meetings?buildingId=123" - invalid buildingId parameter
	 * Expected: empty MeetingDTO array is returned to the client, HTTP Status 200
	 */
	@Test
	public void getMeetingsByBuildingIdInvalidBuildId() throws Exception {
		
		ResponseEntity<MeetingDTO[]> responseEntity = 
			restTemplate.exchange("/api/meetings?buildingId=123", HttpMethod.GET, getRequestEntity(null, "manager1", "manager1"), MeetingDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(responseEntity.getBody().length, 0);
    }

}
