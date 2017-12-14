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

import com.timsedam.buildingmanagement.dto.CommentDTO;
import com.timsedam.buildingmanagement.dto.CreateReportDTO;
import com.timsedam.buildingmanagement.dto.ForwardDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.repository.CommentRepository;
import com.timsedam.buildingmanagement.repository.ReportRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	private static final String URL_PREFIX = "/api/reports/";

	private String getUserToken(String username, String password) {
		UserLoginDTO userLoginData = new UserLoginDTO(username, password);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/api/auth/login", userLoginData,
				String.class);
		return responseEntity.getBody();
	}

	private HttpEntity<Object> getRequestEntity(Object params, String username, String password) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Auth-Token", getUserToken(username, password));

		HttpEntity<Object> requestEntity = new HttpEntity<Object>(params, headers);
		return requestEntity;
	}

	/**
	 * Residence resident send POST request to "/api/reports/create" with valid
	 * dto parameter Expected: id of new Report and HTTP Status 201 (CREATED)
	 */
	@Test
	public void create() throws Exception {

		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 3, new ArrayList<String>());

		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(URL_PREFIX + "create/",
				getRequestEntity(validReportDTO, "mladen", "mladen"), Long.class);

		Long id = responseEntity.getBody();
		Report report = reportRepository.findOne(id);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("mladen", report.getSender().getUsername());
		assertEquals("kvar", report.getDescription());
		assertEquals(3, report.getLocation().getId());

		reportRepository.delete(id);
	}

	/**
	 * Residence owner send POST request to "/api/reports/create" with valid dto
	 * parameter Expected: id of new Report and HTTP Status 201 (CREATED)
	 */
	@Test
	public void ownerCreate() throws Exception {

		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 3, new ArrayList<String>());

		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(URL_PREFIX + "create/",
				getRequestEntity(validReportDTO, "vaso", "vaso"), Long.class);

		Long id = responseEntity.getBody();
		Report report = reportRepository.findOne(id);
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("vaso", report.getSender().getUsername());
		assertEquals("kvar", report.getDescription());
		assertEquals(3, report.getLocation().getId());

		reportRepository.delete(id);
	}

	/**
	 * User without permission send POST request to "/api/reports/create"
	 * Expected: HTTP status 403 (FORBIDDEN)
	 */
	@Test
	public void invalidRole() throws Exception {

		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 3, new ArrayList<String>());

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "create/",
				getRequestEntity(validReportDTO, "admin", "admin"), String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

	}

	/**
	 * Resident from the other building send POST request to
	 * "/api/reports/create" Expected: HTTP status 409 (CONFLICT)
	 */
	@Test
	public void fromOtherBuilding() throws Exception {

		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 3, new ArrayList<String>());

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "create/",
				getRequestEntity(validReportDTO, "ivan", "ivan"), String.class);

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

	}

	/**
	 * User send POST request to "/api/reports/forward" with not existing report
	 * Expected: HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void forwardAtNotExistingReport() throws Exception {

		ForwardDTO badForwardDTO = new ForwardDTO(3, -95);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "forward/",
				getRequestEntity(badForwardDTO, "ivan", "ivan"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}

	/**
	 * User send POST request to "/api/reports/forward" with report on which he
	 * isn't current holder 
	 * Expected: HTTP status 409 (CONFLICT)
	 */
	@Test
	public void notCurrentHolder() throws Exception {

		ForwardDTO badForwardDTO = new ForwardDTO(3, 1);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "forward/",
				getRequestEntity(badForwardDTO, "ivan", "ivan"), String.class);

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

	}
	
	/**
	 * User send POST request to "/api/reports/forward" with not existing receiver
	 * Expected: HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void receiverNotExists() throws Exception {

		ForwardDTO badForwardDTO = new ForwardDTO(500, 1);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "forward/",
				getRequestEntity(badForwardDTO, "ivan", "ivan"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}
	
	/**
	 * User send valid POST request to "/api/reports/forward"
	 * Expected: Set new holder on report and send back HTTP status 200 (OK)
	 */
	@Test
	public void forward() throws Exception {

		long report = 1;
		long to = 4; 
		long current = 3;
		ForwardDTO validForwardDTO = new ForwardDTO(to, report);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "forward/",
				getRequestEntity(validForwardDTO, "vaso", "vaso"), String.class);

		long newHolder = reportRepository.findOne(report).getCurrentHolder().getForwardedTo().getId();
		long oldHolder = reportRepository.findOne(report).getCurrentHolder().getForwarder().getId();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(current, oldHolder);
		assertEquals(to, newHolder);

	}
	
	/**
	 * User send POST request to "/api/reports/comment" with not existing report
	 * Expected: HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void commentAtNotExistingReport() throws Exception {

		CommentDTO badDTO = new CommentDTO("komentar", -59);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "comment/",
				getRequestEntity(badDTO, "ivan", "ivan"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

	}
	
	/**
	 * User send POST request to "/api/reports/comment" with empty comment
	 * Expected: HTTP status 400 (BAD_REQUEST)
	 */
	@Test
	public void emptyComment() throws Exception {

		CommentDTO badDTO = new CommentDTO("", 1);

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "comment/",
				getRequestEntity(badDTO, "ivan", "ivan"), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

	}
	
	/**
	 * User send POST request to "/api/reports/comment" with not existing report
	 * Expected: HTTP status 200 (OK)
	 */
	@Test
	public void postComment() throws Exception {

		long report = 1;
		String komentar = "komentar";
		
		CommentDTO validDTO = new CommentDTO(komentar, report);

		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(URL_PREFIX + "comment/",
				getRequestEntity(validDTO, "ivan", "ivan"), Long.class);

		Long id = responseEntity.getBody();
		Comment comment = commentRepository.findOne(id);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(komentar, comment.getData());
		

	}
}
