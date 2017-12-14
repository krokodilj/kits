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

import com.timsedam.buildingmanagement.dto.CreateReportDTO;
import com.timsedam.buildingmanagement.dto.UserLoginDTO;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.repository.ReportRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ReportRepository reportRepository;

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
	 * dto parameter 
	 * Expected: id of new Report and HTTP Status 201 (CREATED)
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
	 * Residence owner send POST request to "/api/reports/create" with valid
	 * dto parameter 
	 * Expected: id of new Report and HTTP Status 201 (CREATED)
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
	 * Resident from the other building send POST request to "/api/reports/create"
	 * Expected: HTTP status 409 (CONFLICT)
	 */
	@Test
	public void fromOtherBuilding() throws Exception {

		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 3, new ArrayList<String>());

		ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL_PREFIX + "create/",
				getRequestEntity(validReportDTO, "ivan", "ivan"), String.class);

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());

	}
}
