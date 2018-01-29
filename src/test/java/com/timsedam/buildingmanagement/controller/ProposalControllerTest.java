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

import com.timsedam.buildingmanagement.dto.request.ProposalCreateDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.response.ProposalDTO;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.repository.ProposalRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProposalControllerTest {
	
	private static final String URL_PREFIX = "/api/proposals/";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private ProposalRepository proposalRepository;
	
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
	 * POST request to "/api/proposals/" with valid ProposalCreateDTO
	 * Expected: new Proposal's id is returned, HTTP Status 201 CREATED
	 */
	@Test
	public void createProposalWithReport() throws Exception {
		ProposalCreateDTO proposalCreateDTO = 
				new ProposalCreateDTO("proposal content", LocalDateTime.now(), 1L, 1L);
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalCreateDTO, "resident1", "resident1"), Long.class);
		
		Long proposalId = responseEntity.getBody();
		Proposal proposal = proposalRepository.findOne(proposalId);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(proposalCreateDTO.getContent(), proposal.getContent());
		if(proposalCreateDTO.getSuggestedAt().truncatedTo(ChronoUnit.MINUTES)
				.equals(proposal.getSuggestedAt().truncatedTo(ChronoUnit.MINUTES)))
			assert(true);
		else
			assert(false);

		proposalRepository.delete(proposalId);
    }
	
	/**
	 * POST request to "/api/proposals/" with valid ProposalCreateDTO parameter without Report attached
	 * Expected: new Proposal's id is returned, HTTP Status 201 CREATED
	 */
	@Test
	public void createProposalWithoutReport() throws Exception {
		ProposalCreateDTO proposalCreateDTO = 
				new ProposalCreateDTO("proposal content", LocalDateTime.now(), null, 1L);
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalCreateDTO, "resident1", "resident1"), Long.class);
		
		Long proposalId = responseEntity.getBody();
		Proposal proposal = proposalRepository.findOne(proposalId);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(proposalCreateDTO.getContent(), proposal.getContent());
		if(proposalCreateDTO.getSuggestedAt().truncatedTo(ChronoUnit.MINUTES)
				.equals(proposal.getSuggestedAt().truncatedTo(ChronoUnit.MINUTES)))
			assert(true);
		else
			assert(false);

		proposalRepository.delete(proposalId);
    }
	
	/**
	 * POST request to "/api/proposals/" with invalid ProposalCreateDTO parameter - no content
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createProposalWithoutContent() throws Exception {
		ProposalCreateDTO proposalCreateDTO = new ProposalCreateDTO(null, LocalDateTime.now(), 1L, 1L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalCreateDTO, "resident1", "resident1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'content' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/proposals/" with invalid ProposalCreateDTO parameter - no suggestedAt
	 * Expected: error message is returned, HTTP Status 422
	 */
	@Test
	public void createProposalWithoutSuggestedAt() throws Exception {
		ProposalCreateDTO proposalCreateDTO = new ProposalCreateDTO("content", null, 1L, 1L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalCreateDTO, "resident1", "resident1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'suggestedAt' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid ProposalCreateDTO parameter - Report attached doesn't concern sender's Building
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createProposalWithBadAttachedReport() throws Exception {
		ProposalCreateDTO proposalCreateDTO = new ProposalCreateDTO("content", LocalDateTime.now(), 5L, 1L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalCreateDTO, "resident1", "resident1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("Report with id: 5 is not attached to Building with id: 1", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid ProposalCreateDTO parameter - User is not an Owner or Resident in Building
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createProposalUserNotResidentOrOwner() throws Exception {
		ProposalCreateDTO proposalCreateDTO = new ProposalCreateDTO("content", LocalDateTime.now(), 5L, 5L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalCreateDTO, "resident1", "resident1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("User with id: 16 is not a Resident or Owner in Building with id: 5", responseEntity.getBody());
	}
	
	/**
	 * GET request to "/api/proposals/{proposalId}" with valid proposalId parameter
	 * Expected: ProposalDTO is returned, HTTP Status 200 OK
	 */
	@Test
	public void getProposal() throws Exception {
		ResponseEntity<ProposalDTO> responseEntity = 
				restTemplate.exchange("/api/proposals/1", HttpMethod.GET, getRequestEntity(null, "resident1", "resident1"), ProposalDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/proposals/{proposalId}" with invalid proposalId parameter - Proposal with specified id doesnt exist 
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void invalidMeetingIdGetMeeting() throws Exception {
		ResponseEntity<String> responseEntity = 
				restTemplate.exchange("/api/proposals/156", HttpMethod.GET, getRequestEntity(null, "resident1", "resident1"), String.class);
	
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Proposal with id: 156 doesn't exist.", responseEntity.getBody());
    }
	
	/**
	 * GET request to "/api/proposals?buildingId=1&proposal_status=OPEN"
	 * Expected: ProposalDTO Array is returned, HTTP Status 200 OK
	 */
	@Test
	public void getProposalsByBuildingIdAndProposalStatus() throws Exception {
		
		ResponseEntity<ProposalDTO[]> responseEntity = restTemplate.exchange(
				"/api/proposals?building_id=1&proposal_status=OPEN", HttpMethod.GET, getRequestEntity(null, "resident1", "resident1"), ProposalDTO[].class);
		ProposalDTO[] data = responseEntity.getBody();
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(1, data.length);
    }

}
