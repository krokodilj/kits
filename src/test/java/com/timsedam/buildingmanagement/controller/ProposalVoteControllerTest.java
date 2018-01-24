package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

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

import com.timsedam.buildingmanagement.dto.request.ProposalVoteCastDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.model.ProposalVote;
import com.timsedam.buildingmanagement.model.ProposalVoteValue;
import com.timsedam.buildingmanagement.repository.ProposalVoteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProposalVoteControllerTest {
	
	private static final String URL_PREFIX = "/api/proposal_votes/";
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ProposalVoteRepository proposalVoteRepository;

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
	 * POST request to "/api/proposal_votes/" with valid ProposalVoteCastDTO parameter
	 * Expected: new Proposal's id is returned, HTTP Status 201 CREATED
	 */
	@Test
	public void castProposalVote() throws Exception {
		ProposalVoteCastDTO proposalVoteDTO = new ProposalVoteCastDTO(ProposalVoteValue.FOR, 1L);
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalVoteDTO, "owner1", "owner1"), Long.class);
		
		Long proposalVoteId = responseEntity.getBody();
		ProposalVote proposalVote = proposalVoteRepository.findOne(proposalVoteId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(proposalVote.getVote(), proposalVoteDTO.getValue());
    }
	
	/**
	 * POST request to "/api/proposal_votes/" with invalid ProposalVoteCastDTO parameter
	 * User doesn't own an Apartment in the Building Proposal is bound to
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void castProposalVoteInvalidProposalId() throws Exception {
		ProposalVoteCastDTO proposalVoteDTO = new ProposalVoteCastDTO(ProposalVoteValue.FOR, 5L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalVoteDTO, "owner1", "owner1"), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("User with id: 36 is not an ApartmentOwner in Building with id: 5", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/proposal_votes/" with invalid ProposalVoteCastDTO parameter - no ProposalId
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void castProposalVoteNoProposalId() throws Exception {
		ProposalVoteCastDTO proposalVoteDTO = new ProposalVoteCastDTO(ProposalVoteValue.FOR, null);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalVoteDTO, "owner1", "owner1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'proposalId' not provided", responseEntity.getBody());
    }
	
	/**
	 * POST request to "/api/proposal_votes/" with invalid ProposalVoteCastDTO parameter - no value
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void castProposalVoteNoValue() throws Exception {
		
		ProposalVoteCastDTO proposalVoteDTO = new ProposalVoteCastDTO(null, 1L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(proposalVoteDTO, "owner1", "owner1"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'value' not provided", responseEntity.getBody());
    }
	
}
