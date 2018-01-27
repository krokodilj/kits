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

import com.timsedam.buildingmanagement.dto.request.OfferedAnswerSendDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AnswerControllerTest {
	
private static final String URL_PREFIX = "/api/answers/";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
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
	 * POST request to "/api/answers/offered_answer/" with valid OfferedAnswerCreateDTO
	 * Expected: Question's id is returned to the client, HTTP Status 201 CREATED
	 */
	@Test
	public void giveAnswer() throws Exception {
		Long choiceQuestionId = 1L;
		Long suggestedAnswerId = 1L;
		
		OfferedAnswerSendDTO dto = new OfferedAnswerSendDTO(suggestedAnswerId, choiceQuestionId);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "offered_answer/send/", getRequestEntity(dto, "manager1", "manager1"), String.class);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}
