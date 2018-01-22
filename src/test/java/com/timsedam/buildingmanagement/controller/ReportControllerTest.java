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

import com.timsedam.buildingmanagement.dto.request.AcceptBidDTO;
import com.timsedam.buildingmanagement.dto.request.CommentDTO;
import com.timsedam.buildingmanagement.dto.request.CreateReportDTO;
import com.timsedam.buildingmanagement.dto.request.ForwardDTO;
import com.timsedam.buildingmanagement.dto.request.UserLoginDTO;
import com.timsedam.buildingmanagement.dto.response.BidDTO;
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
	 * POST request to "/api/reports/" with valid dto parameter
	 * Expected: id of new Report and HTTP Status 201 (CREATED)
	 */
	@Test
	public void create() throws Exception {
		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<Long> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "resident1", "resident1"), Long.class);

		Long reportId = responseEntity.getBody();
		Report report = reportRepository.findOne(reportId);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("resident1", report.getSender().getUsername());
		assertEquals("kvar", report.getDescription());
		assertEquals(1, report.getLocation().getId());

		reportRepository.delete(reportId);
	}

	/**
	 * Residence owner send POST request to "/api/reports/create" with valid dto parameter
	 * Expected: id of new Report and HTTP Status 201 (CREATED)
	 */
	@Test
	public void ownerCreate() throws Exception {
		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<Long> responseEntity = 
				restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "owner1", "owner1"), Long.class);

		Long id = responseEntity.getBody();
		Report report = reportRepository.findOne(id);
		
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("owner1", report.getSender().getUsername());
		assertEquals("kvar", report.getDescription());
		assertEquals(1, report.getLocation().getId());

		reportRepository.delete(id);
	}

	/**
	 * User without permission send POST request to "/api/reports/"
	 * Expected: HTTP status 403 (FORBIDDEN)
	 */
	@Test
	public void invalidRole() throws Exception {
		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "admin1", "admin1"), String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	/**
	 * Resident from the other building send POST request to "/api/reports/" 
	 * Expected: HTTP status 409 (CONFLICT)
	 */
	@Test
	public void fromOtherBuilding() throws Exception {
		CreateReportDTO validReportDTO = new CreateReportDTO("kvar", 1, new ArrayList<String>());

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX, getRequestEntity(validReportDTO, "resident20", "resident20"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
	}
	
	/**
	 * User send valid POST request to "/api/reports/forward"
	 * Expected: Set new holder on report and send back HTTP status 200 (OK)
	 */
	@Test
	public void forward() throws Exception {
		long reportId = 1;
		long to = 12; 
		long current = 11;
		ForwardDTO validForwardDTO = new ForwardDTO(to, reportId);

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
	 * Expected: HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void forwardAtNotExistingReport() throws Exception {

		ForwardDTO badForwardDTO = new ForwardDTO(3, -95);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "forward/", getRequestEntity(badForwardDTO, "manager1", "manager1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Report with id: -95 doesn't exist.", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/forward" with report on which he isn't current holder 
	 * Expected: HTTP status 409 (CONFLICT)
	 */
	@Test
	public void notCurrentHolder() throws Exception {

		ForwardDTO badForwardDTO = new ForwardDTO(3, 5);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "forward/", getRequestEntity(badForwardDTO, "manager1", "manager1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("User with id: 11 is not current holder of report with id: 5", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/forward" with not existing receiver
	 * Expected: HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void receiverNotExists() throws Exception {

		ForwardDTO badForwardDTO = new ForwardDTO(500, 1);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "forward/", getRequestEntity(badForwardDTO, "manager1", "manager1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("User with id: 500 doesn't exist.", responseEntity.getBody());
	}
	
	
	/**
	 * User send POST request to "/api/reports/comment" with valid DTO
	 * Expected: HTTP status 200 (OK)
	 */
	@Test
	public void postComment() throws Exception {
		long report = 1;
		String data = "comment data";
		
		CommentDTO validDTO = new CommentDTO(data, report);

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
	 * Expected: HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void commentAtNotExistingReport() throws Exception {

		CommentDTO badDTO = new CommentDTO("comment data", -59);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "comment/", getRequestEntity(badDTO, "resident1", "resident1"), String.class);

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Report with id: -59 doesn't exist.", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/comment" with empty comment
	 * Expected: HTTP status 422 (UNPROCESSABLE_ENTITY)
	 */
	@Test
	public void emptyComment() throws Exception {

		CommentDTO badDTO = new CommentDTO(null, 1);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "comment/", getRequestEntity(badDTO, "resident1", "resident1"), String.class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
		assertEquals("'data' not provided", responseEntity.getBody());
	}
	
	
	/**
	 * User send POST request to "/api/reports/bid" with valid DTO
	 * Expected: Add new bid to report and send back HTTP status 200 (OK)
	 */
	@Test
	public void sendBid() throws Exception {

		long report_id = 1;
		long price = 500;
		String description = "ponuda";
		BidDTO validDTO = new BidDTO(description, price, report_id);

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
	 * Expected: HTTP status 403 (FORBIDDEN)
	 */
	@Test
	public void userWithoutPermissionBid() throws Exception {

		BidDTO badDTO = new BidDTO("poslednja ponuda", 200, 1);

		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "bid/", getRequestEntity(badDTO, "resident1", "resident1"), String.class);

		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}
	
	/**
	 * User send POST request to "/api/reports/bid" with not existing report
	 * Expected: HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void bidAtNotExistingReport() throws Exception {

		BidDTO badDTO = new BidDTO("poslednja ponuda", 200, -51);

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
	 * send back HTTP status 200 (OK)
	 */
	@Test
	public void acceptBid() throws Exception {
		
		long bid_id = 1;
		AcceptBidDTO validDTO = new AcceptBidDTO(bid_id);
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
	 * Expected: send back HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void bidNotExists() throws Exception {
		
		long bid_id = -55;
		AcceptBidDTO validDTO = new AcceptBidDTO(bid_id);
		
		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "acceptBid/", getRequestEntity(validDTO, "manager1", "manager1"), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Bid with id: -55 doesn't exist.", responseEntity.getBody());
	}
	
	/**
	 * User send POST request to "/api/reports/acceptBid" with invalid bid (already ACCEPTED)
	 * Expected: send back HTTP status 404 (NOT_FOUND)
	 */
	@Test
	public void alreadyAccepted() throws Exception {
		
		long bid_id = 5;
		AcceptBidDTO validDTO = new AcceptBidDTO(bid_id);
		
		ResponseEntity<String> responseEntity = 
			restTemplate.postForEntity(URL_PREFIX + "acceptBid/", getRequestEntity(validDTO, "manager5", "manager5"), String.class);
		
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Bid must have status 'OPEN'.", responseEntity.getBody());
	}
	
}
