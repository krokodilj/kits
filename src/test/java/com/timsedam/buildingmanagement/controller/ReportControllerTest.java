package com.timsedam.buildingmanagement.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

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

import com.timsedam.buildingmanagement.dto.request.BidAcceptDTO;
import com.timsedam.buildingmanagement.dto.request.BidSendDTO;
import com.timsedam.buildingmanagement.dto.request.CommentCreateDTO;
import com.timsedam.buildingmanagement.dto.request.ForwardCreateDTO;
import com.timsedam.buildingmanagement.dto.request.ReportCreateDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.repository.BidRepository;
import com.timsedam.buildingmanagement.repository.CommentRepository;
import com.timsedam.buildingmanagement.repository.ForwardRepository;
import com.timsedam.buildingmanagement.repository.ReportRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {
	
	private static final String URL_PREFIX = "/api/reports/";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ReportRepository reportRepository;
	
	@Autowired
	private ForwardRepository forwardRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private BidRepository bidRepository;

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
	 * POST request to "/api/reports/" with valid dto parameter
	 * Expected: new Report's id is returned, HTTP Status 201 CREATED
	 */
	@Test
	public void create() throws Exception {
		ReportCreateDTO validReportDTO = new ReportCreateDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "resident1", "resident1"), String.class);

		Long reportId = Long.valueOf(responseEntity.getBody());
		Report report = reportRepository.findOne(reportId);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("resident1", report.getSender().getUsername());
		assertEquals("kvar", report.getDescription());
		assertEquals(1, report.getLocation().getId());

		reportRepository.delete(reportId);
	}

	/**
	 * Residence owner send POST request to "/api/reports/create" with valid dto parameter
	 * Expected: new Report's id is returned, HTTP Status 201 CREATED
	 */
	@Test
	public void ownerCreate() throws Exception {
		ReportCreateDTO validReportDTO = new ReportCreateDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<String> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "owner1", "owner1"), String.class);

		Long id = Long.valueOf(responseEntity.getBody());
		Report report = reportRepository.findOne(id);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("owner1", report.getSender().getUsername());
		assertEquals("kvar", report.getDescription());
		assertEquals(1, report.getLocation().getId());

		reportRepository.delete(id);
	}

	/**
	 * User without permission send POST request to "/api/reports/"
	 * Expected: HTTP Status 403 FORBIDDEN
	 */
	@Test
	public void invalidRole() throws Exception {
		ReportCreateDTO validReportDTO = new ReportCreateDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "admin1", "admin1"), String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	/**
	 * Resident from the other building send POST request to "/api/reports/" 
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void fromOtherBuilding() throws Exception {
		ReportCreateDTO validReportDTO = new ReportCreateDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "resident20", "resident20"), String.class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
	}
	
	/**
	 * User send valid POST request to "/api/reports/forward"
	 * Expected: HTTP Status 200 OK
	 */
	@Test
	public void forward() throws Exception {
		long reportId = 1;
		long to = 12; 
		long current = 11;
		ForwardCreateDTO validForwardDTO = new ForwardCreateDTO(to, reportId);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "forward/", getRequestEntity(validForwardDTO, "manager1", "manager1"), String.class);

		Report report = reportRepository.findOne(1L);
		long newHolder = report.getCurrentHolder().getForwardedTo().getId();
		long oldHolder = report.getCurrentHolder().getForwarder().getId();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(current, oldHolder);
		assertEquals(to, newHolder);

		Forward originalHolder = forwardRepository.findOne(1L);
		forwardRepository.delete(Long.parseLong(responseEntity.getBody()));
		report.setCurrentHolder(originalHolder);
		reportRepository.save(report);
	}
	
	/**
	 * User send POST request to "/api/reports/forward" with not existing report
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void forwardAtNotExistingReport() throws Exception {

		ForwardCreateDTO badForwardDTO = new ForwardCreateDTO(3, -95);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "forward/", getRequestEntity(badForwardDTO, "manager1", "manager1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Report with id: -95 doesn't exist.", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/forward" with report on which he isn't current holder 
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void notCurrentHolder() throws Exception {

		ForwardCreateDTO badForwardDTO = new ForwardCreateDTO(3, 5);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "forward/", getRequestEntity(badForwardDTO, "manager1", "manager1"), String.class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("User with id: 11 is not current holder of report with id: 5", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/forward" with not existing receiver
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void receiverNotExists() throws Exception {

		ForwardCreateDTO badForwardDTO = new ForwardCreateDTO(500, 1);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "forward/", getRequestEntity(badForwardDTO, "manager1", "manager1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("User with id: 500 doesn't exist.", responseEntity.getBody());
	}
	
	
	/**
	 * User send POST request to "/api/reports/comment" with valid DTO
	 * Expected: HTTP status 200 OK
	 */
	@Test
	public void postComment() throws Exception {
		long report = 1;
		String data = "comment data";
		
		CommentCreateDTO validDTO = new CommentCreateDTO(data, report);

		ResponseEntity<Long> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "comment/", getRequestEntity(validDTO, "resident1", "resident1"), Long.class);

		Long commentId = responseEntity.getBody();
		Comment comment = commentRepository.findOne(commentId);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(data, comment.getData());

		commentRepository.delete(commentId);
	}

	/**
	 * User send POST request to "/api/reports/comment" with not existing report
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void commentAtNotExistingReport() throws Exception {

		CommentCreateDTO badDTO = new CommentCreateDTO("comment data", -59);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "comment/", getRequestEntity(badDTO, "resident1", "resident1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Report with id: -59 doesn't exist.", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/comment" with empty comment
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void emptyComment() throws Exception {

		CommentCreateDTO badDTO = new CommentCreateDTO(null, 1);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "comment/", getRequestEntity(badDTO, "resident1", "resident1"), String.class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'data' not provided", responseEntity.getBody());
	}
	
	
	/**
	 * User send POST request to "/api/reports/bid" with valid DTO
	 * Expected: HTTP Status 200 OK
	 */
	@Test
	public void sendBid() throws Exception {

		long report_id = 1;
		long price = 500;
		String description = "ponuda";
		BidSendDTO validDTO = new BidSendDTO(description, price, report_id);

		Report report = reportRepository.findOne(report_id);
		List<Bid> bids = bidRepository.findByReportBid(report);
		
		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "bid/", getRequestEntity(validDTO, "company1", "company1"), String.class);
		
		List<Bid> newBids = bidRepository.findByReportBid(report);
		
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(newBids.size(), bids.size() + 1);
		
		bidRepository.delete(Long.parseLong(responseEntity.getBody()));
	}
	
	/**
	 * User without permission send POST request to "/api/reports/bid"
	 * Expected: HTTP status 403 FORBIDDEN
	 */
	@Test
	public void userWithoutPermissionBid() throws Exception {

		BidSendDTO badDTO = new BidSendDTO("poslednja ponuda", 200, 1);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "bid/", getRequestEntity(badDTO, "resident1", "resident1"), String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	/**
	 * User send POST request to "/api/reports/bid" with not existing report
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void bidAtNotExistingReport() throws Exception {

		BidSendDTO badDTO = new BidSendDTO("poslednja ponuda", 200, -51);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "bid/", getRequestEntity(badDTO, "company1", "company1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Report with id: -51 doesn't exist.", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/acceptBid" with valid DTO
	 * Expected: 
	 * 1 - Change bid status to ACCEPTED
	 * 2 - Change bid status to other bids from same report to DECLINED
	 * 3 - Change report status to CLOSED
	 * Expected: error message is returned, HTTP Status 200 OK
	 */
	@Test
	public void acceptBid() throws Exception {
		
		long bid_id = 1;
		BidAcceptDTO validDTO = new BidAcceptDTO(bid_id);
		Bid bid = bidRepository.findOne(bid_id);
		
		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "acceptBid/", getRequestEntity(validDTO, "manager1", "manager1"), String.class);
		
		Bid newBid = bidRepository.findOne(bid_id);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("OPEN", bid.getStatus());
		assertEquals("ACCEPTED", newBid.getStatus());
		assertEquals("CLOSED", newBid.getReport().getStatus());
		
		bidRepository.setStatus("OPEN", bid_id);
	}
	
	/**
	 * User send POST request to "/api/reports/acceptBid" with not existing bid
	 * Expected: error message is returned, HTTP Status 404 NOT_FOUND
	 */
	@Test
	public void bidNotExists() throws Exception {
		
		long bid_id = -55;
		BidAcceptDTO validDTO = new BidAcceptDTO(bid_id);
		
		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "acceptBid/", getRequestEntity(validDTO, "manager1", "manager1"), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Bid with id: -55 doesn't exist.", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/acceptBid" with invalid bid (already ACCEPTED)
	 * Expected: error message is returned, HTTP Status 422 UNPROCESSABLE_ENTITY
	 */
	@Test
	public void alreadyAccepted() throws Exception {
		
		long bid_id = 5;
		BidAcceptDTO validDTO = new BidAcceptDTO(bid_id);
		
		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "acceptBid/", getRequestEntity(validDTO, "manager5", "manager5"), String.class);
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("Bid must have status 'OPEN'.", responseEntity.getBody());
	}
	
}
