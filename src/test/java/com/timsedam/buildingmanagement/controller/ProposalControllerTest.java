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

import com.timsedam.buildingmanagement.dto.ProposalCreateDTO;
import com.timsedam.buildingmanagement.dto.ProposalDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.repository.ProposalRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class ProposalControllerTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private ProposalRepository proposalRepository;
	
	private static final String URL_PREFIX = "/api/proposals/";
	
	private ProposalCreateDTO validProposalCreateDTO = 
			new ProposalCreateDTO("proposal content", LocalDateTime.now(), 1L, 6L);
	
	private String getProposerToken() {
		UserLoginDTO userLoginData = new UserLoginDTO("llaraway5", "TunNbXAEOo");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity("/api/auth/login", userLoginData, String.class);
		return responseEntity.getBody();
	}
	
	private HttpEntity<Object> getRequestEntity(Object params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", getProposerToken());
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
		return requestEntity;
	}
	
	/**
	 * POST request to "/api/meetings/" with valid ProposalCreateDTO parameter with Report attached
	 * Expected: new ProposalDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void createProposalWithReport() throws Exception {
		
		ResponseEntity<ProposalDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validProposalCreateDTO), ProposalDTO.class);
		
		ProposalDTO proposalFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(validProposalCreateDTO.getContent(), proposalFromResponse.getContent());
		if(validProposalCreateDTO.getSuggestedAt().equals(proposalFromResponse.getSuggestedAt()))
			assert(true);
		else
			assert(false);

		proposalRepository.delete(proposalFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/meetings/" with valid ProposalCreateDTO parameter without Report attached
	 * Expected: new ProposalDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void createProposalWithoutReport() throws Exception {
		
		ProposalCreateDTO proposalCreateDTO = new ProposalCreateDTO(validProposalCreateDTO);
		proposalCreateDTO.setAttachedReport(null);
		
		ResponseEntity<ProposalDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalCreateDTO), ProposalDTO.class);
		
		ProposalDTO proposalFromResponse = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(proposalCreateDTO.getContent(), proposalFromResponse.getContent());
		if(proposalCreateDTO.getSuggestedAt().equals(proposalFromResponse.getSuggestedAt()))
			assert(true);
		else
			assert(false);

		proposalRepository.delete(proposalFromResponse.getId());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid ProposalCreateDTO parameter - no content
	 * Expected: no ProposalDTO is returned to the client, HTTP Status 422
	 */
	@Test
	public void createProposalWithoutContent() throws Exception {
		
		ProposalCreateDTO invalidProposalCreateDTO = new ProposalCreateDTO(validProposalCreateDTO);
		invalidProposalCreateDTO.setContent(null);
		
		ResponseEntity<ProposalDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidProposalCreateDTO), ProposalDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid ProposalCreateDTO parameter - no suggestedAt
	 * Expected: no ProposalDTO is returned to the client, HTTP Status 422
	 */
	@Test
	public void createProposalWithoutSuggestedAt() throws Exception {
		
		ProposalCreateDTO invalidProposalCreateDTO = new ProposalCreateDTO(validProposalCreateDTO);
		invalidProposalCreateDTO.setSuggestedAt(null);
		
		ResponseEntity<ProposalDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidProposalCreateDTO), ProposalDTO.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/meetings/" with invalid ProposalCreateDTO parameter - Report attached doesnt concern sender's Building
	 * Expected: no ProposalDTO is returned to the client, HTTP Status 400
	 */
	@Test
	public void createProposalWithBadAttachedReport() throws Exception {
		
		ProposalCreateDTO invalidProposalCreateDTO = new ProposalCreateDTO(validProposalCreateDTO);
		invalidProposalCreateDTO.setAttachedReport(10L);
		
		ResponseEntity<ProposalDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidProposalCreateDTO), ProposalDTO.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/proposals/{proposalId}" with valid proposalId parameter 
	 * Expected: ProposalDTO is returned to the client, HTTP Status 200
	 */
	@Test
	public void getProposal() throws Exception {
		
		ResponseEntity<ProposalDTO> responseEntity = restTemplate.exchange(
				"/api/proposals/1", HttpMethod.GET, getRequestEntity(null), ProposalDTO.class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/proposals/{proposalId}" with invalid proposalId parameter - Proposal with specified id doesnt exist 
	 * Expected: no ProposalDTO is returned to the client, HTTP Status 204
	 */
	@Test
	public void invalidMeetingIdGetMeeting() throws Exception {
		
		ResponseEntity<ProposalDTO> responseEntity = restTemplate.exchange(
				"/api/proposals/156", HttpMethod.GET, getRequestEntity(null), ProposalDTO.class);
		
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }
	
	/**
	 * GET request to "/api/proposals?buildingId=6"
	 * Expected: ProposalDTO array is returned to the client, HTTP Status 200
	 */
	@Test
	public void getProposalsByBuildingId() throws Exception {
		
		ResponseEntity<ProposalDTO[]> responseEntity = restTemplate.exchange(
				"/api/proposals?buildingId=6", HttpMethod.GET, getRequestEntity(null), ProposalDTO[].class);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		
    }
	
	

}
