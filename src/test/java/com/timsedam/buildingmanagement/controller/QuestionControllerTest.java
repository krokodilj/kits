package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

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

import com.timsedam.buildingmanagement.dto.request.ChoiceQuestionCreateDTO;
import com.timsedam.buildingmanagement.dto.request.AnswerCreateDTO;
import com.timsedam.buildingmanagement.dto.request.OpenEndedQuestionCreateDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.model.Answer;
import com.timsedam.buildingmanagement.model.ChoiceQuestion;
import com.timsedam.buildingmanagement.model.OfferedAnswer;
import com.timsedam.buildingmanagement.model.OpenEndedQuestion;
import com.timsedam.buildingmanagement.repository.AnswerRepository;
import com.timsedam.buildingmanagement.repository.QuestionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuestionControllerTest {
	
	private static final String URL_PREFIX = "/api/questions/";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
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
	 * POST request to "/api/questions/choice_question/" with valid ChoiceQuestionCreateDTO
	 * Expected: Question's id is returned to the client, HTTP Status 201 CREATED
	 */
	@Test
	public void createChoiceQuestion() throws Exception {
		ChoiceQuestionCreateDTO dto = 
				new ChoiceQuestionCreateDTO("QUESTION TEXT", 1L, new ArrayList<AnswerCreateDTO>());
		AnswerCreateDTO answer1 = new AnswerCreateDTO("Answer no 1");
		AnswerCreateDTO answer2 = new AnswerCreateDTO("Answer no 2");
		dto.getOfferedAnswers().add(answer1);
		dto.getOfferedAnswers().add(answer2);
		
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "choice_question/", getRequestEntity(dto, "manager1", "manager1"), String.class);
		
		String questionId = responseEntity.getBody();
		ChoiceQuestion question = (ChoiceQuestion) questionRepository.findOne(Long.parseLong(questionId));
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(dto.getQuestionText(), question.getQuestionText());
		assertEquals(dto.getQuestionForm(), question.getQuestionForm().getId());

    }
	
	/**
	 * POST request to "/api/questions/open_ended_question/" with valid OpenEndedQuestionCreateDTO
	 * Expected: Question's id is returned to the client, HTTP Status 201 CREATED
	 */
	@Test
	public void createOpenEndedQuestion() throws Exception {
		OpenEndedQuestionCreateDTO dto = new OpenEndedQuestionCreateDTO("QUESTION TEXT", 1L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "open_ended_question/", getRequestEntity(dto, "manager1", "manager1"), String.class);
		
		String questionId = responseEntity.getBody();
		OpenEndedQuestion question = (OpenEndedQuestion) questionRepository.findOne(Long.parseLong(questionId));
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(dto.getQuestionText(), question.getQuestionText());
		assertEquals(dto.getQuestionForm(), question.getQuestionForm().getId());

		questionRepository.delete(question);
    }
	
    /**
     * POST request to "/api/questions/open_ended_question/" sent by unauthorised user
     * Expected: HttpStatus 403 FORBIDDEN
     */
    @Test
    public void registerAdminUnauthorised() {
    	OpenEndedQuestionCreateDTO dto = new OpenEndedQuestionCreateDTO("QUESTION TEXT", 1L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "open_ended_question/", getRequestEntity(dto, "resident1", "resident1"), String.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    
	/**
	 * POST request to "/api/questions/open_ended_question/" - missing title parameter
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createQuestionMissingTitle() {
		OpenEndedQuestionCreateDTO dto = new OpenEndedQuestionCreateDTO(null, 1L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "open_ended_question/", getRequestEntity(dto, "manager1", "manager1"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'questionText' not provided", errorMessage);
    }
	
	/**
	 * POST request to "/api/questions/open_ended_question/" - missing questionForm parameter
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createQuestionMissingQuestionFormParam() {
		OpenEndedQuestionCreateDTO dto = new OpenEndedQuestionCreateDTO("TITLE", null);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "open_ended_question/", getRequestEntity(dto, "manager1", "manager1"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'questionForm' not provided", errorMessage);
    }
	
	/**
	 * POST request to "/api/questions/open_ended_question/" - questionForm parameter invalid
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void createQuestionMissingQuestionForm() {
		OpenEndedQuestionCreateDTO dto = new OpenEndedQuestionCreateDTO("TITLE", 2L);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX + "open_ended_question/", getRequestEntity(dto, "manager1", "manager1"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("QuestionForm with id: 2 doesn't exist.", errorMessage);
    }

}
