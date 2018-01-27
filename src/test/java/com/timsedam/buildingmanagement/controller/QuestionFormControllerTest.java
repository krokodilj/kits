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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.timsedam.buildingmanagement.dto.request.QuestionFormCreateDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.response.QuestionFormDTO;
import com.timsedam.buildingmanagement.model.QuestionForm;
import com.timsedam.buildingmanagement.repository.QuestionFormRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuestionFormControllerTest {
	
	private static final String URL = "/api/question_forms/";
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private QuestionFormRepository questionFormRepository;
	
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
	 * POST request to "/api/question_forms/" with valid QuestionFormCreateDTOs parameter
	 * Expected: new QuestionForm's id is returned, HTTP Status 201 CREATED
	 */
	@Test
	public void createQuestionForm() throws Exception {
		QuestionFormCreateDTO dto = new QuestionFormCreateDTO(1L, "TITLE", "CONTENT");
		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(dto, "manager1", "manager1"), Long.class);
			
		Long questionFormId = responseEntity.getBody();
		QuestionForm questionForm = questionFormRepository.findOne(questionFormId);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(dto.getTitle(), questionForm.getTitle());
		assertEquals(dto.getContent(), questionForm.getContent());
		
		questionFormRepository.delete(questionFormId);
    }
	
    /**
     * POST request to "/api/question_forms/" sent by unauthorised user
     * Expected: HttpStatus 403 FORBIDDEN
     */
    @Test
    public void registerAdminUnauthorised() {
		QuestionFormCreateDTO dto = new QuestionFormCreateDTO(1L, "TITLE", "CONTENT");
		ResponseEntity<?> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(dto, "company1", "company1"), Object.class);

        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
    }
    
	/**
	 * POST request to "/api/question_forms/" - missing buildingId parameter
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createQuestionFormBuildingIdMissing() {
		QuestionFormCreateDTO dto = new QuestionFormCreateDTO(null, "TITLE", "CONTENT");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(dto, "manager1", "manager1"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'buildingId' not provided", errorMessage);
    }
	
	/**
	 * POST request to "/api/question_forms/" - missing title parameter
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createQuestionFormTitleMissing() {
		QuestionFormCreateDTO dto = new QuestionFormCreateDTO(1L, null, "CONTENT");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(dto, "manager1", "manager1"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'title' not provided", errorMessage);
    }
	
	/**
	 * POST request to "/api/question_forms/" - missing content parameter
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createQuestionFormContetMissing() {
		QuestionFormCreateDTO dto = new QuestionFormCreateDTO(1L, "TITLE", null);
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(dto, "manager1", "manager1"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'content' not provided", errorMessage);
    }
	
	/**
	 * POST request to "/api/question_forms/" - missing buildingId parameter
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createQuestionFormBuildingIdInvalid() {
		QuestionFormCreateDTO dto = new QuestionFormCreateDTO(99L, "TITLE", "CONTENT");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(dto, "manager1", "manager1"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Building with id: 99 doesn't exist.", errorMessage);
    }
	
	/**
	 * POST request to "/api/question_forms/" - User is not a Manager in the Building
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void createQuestionFormUserNotResidentOrOwner() {
		QuestionFormCreateDTO dto = new QuestionFormCreateDTO(1L, "TITLE", "CONTENT");
		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL, getRequestEntity(dto, "manager5", "manager5"), String.class);
			
		String errorMessage = responseEntity.getBody();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("User with id: 15 is not a Manager in Building with id: 1", errorMessage);
    }
	
    /**
     * GET request to '/api/question_forms/{questionFormId}'
     * Expected: HTTP Status 200 OK
     */
    @Test
    public void getQuestionFormById() {
        String questionFormId = "1";
        ResponseEntity<QuestionFormDTO> responseEntity = 
        	restTemplate.exchange(URL + questionFormId, HttpMethod.GET, getRequestEntity(null, "manager1", "manager1"), QuestionFormDTO.class);

        QuestionFormDTO form = responseEntity.getBody();
        QuestionForm formFromDB = questionFormRepository.findOne(Long.parseLong(questionFormId));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(formFromDB.getId(), form.getId());
        assertEquals(formFromDB.getTitle(), form.getTitle());
        assertEquals(formFromDB.getContent(), form.getContent());
        assertEquals(formFromDB.getStatus().toString(), form.getStatus());
    }
    
    /**
     * GET request to '/api/residences/{residenceId}' - invalid residenceId
     * Expected: error message is returned, HTTP Status 404 NOT_FOUND
     */
    @Test
    public void getResidenceByIdNotfound(){
        String questionFormId = "999";
        ResponseEntity<String> responseEntity = 
        	restTemplate.exchange(URL + questionFormId, HttpMethod.GET, getRequestEntity(null, "manager1", "manager1"), String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("QuestionForm with id: 999 doesn't exist.", responseEntity.getBody());
    }

}
