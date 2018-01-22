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
import com.timsedam.buildingmanagement.dto.response.ProposalVoteDTO;
import com.timsedam.buildingmanagement.model.ProposalVoteValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProposalVoteControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	private static final String URL_PREFIX = "/api/proposal_votes/";
	
	private ProposalVoteCastDTO validProposalVoteCastDTO = new ProposalVoteCastDTO(ProposalVoteValue.FOR, 2L);

	private String getApartmentOwnerToken() {
		UserLoginDTO userLoginData = new UserLoginDTO("akondratyuk1", "OgI0uDPrYye");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity("/api/auth/login", userLoginData, String.class);
		return responseEntity.getBody();
	}
	
	private HttpEntity<Object> getRequestEntity(Object params) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", getApartmentOwnerToken());
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
		return requestEntity;
	}

	/**
	 * POST request to "/api/proposal_votes/" with valid ProposalVoteCastDTO parameter
	 * Expected: new ProposalVoteDTO is returned to the client, HTTP Status 201
	 */
	@Test
	public void castProposalVote() throws Exception {
		
		ResponseEntity<ProposalVoteDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validProposalVoteCastDTO), ProposalVoteDTO.class);
		
		ProposalVoteDTO responseDTO = responseEntity.getBody();
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(responseDTO.getVote(), validProposalVoteCastDTO.getValue());		
    }
	
	/**
	 * POST request to "/api/proposal_votes/" with invalid ProposalVoteCastDTO parameter
	 * User doesn't own an Apartment in the Building Proposal is bound to
	 * Expected:  HTTP Status 400
	 */
	@Test
	public void castProposalVoteInvalidProposalId() throws Exception {
		
		ProposalVoteCastDTO invalidRequestData = new ProposalVoteCastDTO(validProposalVoteCastDTO);
		invalidRequestData.setProposalId(4L);
		
		ResponseEntity<ProposalVoteDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidRequestData), ProposalVoteDTO.class);
		
		ProposalVoteDTO responseDTO = responseEntity.getBody();
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
	
	
	/**
	 * POST request to "/api/proposal_votes/" with invalid ProposalVoteCastDTO parameter - no ProposalId
	 * Expected:  HTTP Status 422
	 */
	@Test
	public void castProposalVoteNoProposalId() throws Exception {
		
		ProposalVoteCastDTO invalidRequestData = new ProposalVoteCastDTO(validProposalVoteCastDTO);
		invalidRequestData.setProposalId(null);
		
		ResponseEntity<ProposalVoteDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidRequestData), ProposalVoteDTO.class);
		
		ProposalVoteDTO responseDTO = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }
	
	/**
	 * POST request to "/api/proposal_votes/" with invalid ProposalVoteCastDTO parameter - no value
	 * Expected:  HTTP Status 422
	 */
	@Test
	public void castProposalVoteNoValue() throws Exception {
		
		ProposalVoteCastDTO invalidRequestData = new ProposalVoteCastDTO(validProposalVoteCastDTO);
		invalidRequestData.setValue(null);
		
		ResponseEntity<ProposalVoteDTO> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(invalidRequestData), ProposalVoteDTO.class);
		
		ProposalVoteDTO responseDTO = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
    }
	

}
