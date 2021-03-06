package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.AcceptBidDTO;
import com.timsedam.buildingmanagement.dto.request.CommentDTO;
import com.timsedam.buildingmanagement.dto.request.CreateReportDTO;
import com.timsedam.buildingmanagement.dto.request.ForwardDTO;
import com.timsedam.buildingmanagement.dto.response.BidDTO;
import com.timsedam.buildingmanagement.exceptions.BidMissingException;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.exceptions.InvalidStatusException;
import com.timsedam.buildingmanagement.exceptions.ReportMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotReportHolderException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.BidService;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.CommentService;
import com.timsedam.buildingmanagement.service.ForwardService;
import com.timsedam.buildingmanagement.service.ReportService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value = "/api/reports/")
public class ReportController {

	@Autowired
	private UserService userService;

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ForwardService forwardService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private BidService bidService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<?> create(Principal principal, @Valid @RequestBody CreateReportDTO reportDTO, BindingResult validationResult) 
			throws BuildingMissingException, UserNotResidentException, UserMissingException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		
		User reportSender = userService.findOneByUsername(principal.getName());
		Building building = buildingService.findOne(reportDTO.getBuilding());

		Report report = new Report(reportSender, "OPEN", reportDTO.getDescription(), 
				building, reportDTO.getPhotos(), new ArrayList<Comment>(), null);
		
		Forward forward = new Forward(null, building.getManager(), report);
		
		report.setCurrentHolder(forward);
		report = reportService.create(report);
		forwardService.save(forward);
		return new ResponseEntity<Long>(report.getId(), HttpStatus.CREATED);
	}

	@PostMapping(value = "forward", consumes = "application/json")
	public ResponseEntity<?> forward(Principal principal, @Valid @RequestBody ForwardDTO forwardDTO, BindingResult validationResult)
			throws UserMissingException, ReportMissingException, UserNotReportHolderException {
		
		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		
		User sender = userService.findOneByUsername(principal.getName());
		Report report = reportService.findOne(forwardDTO.getReport());
		User receiver = userService.findOne(forwardDTO.getTo());

		Forward forward = new Forward(sender, receiver, report);
		forward = forwardService.create(forward);

		reportService.setCurrentHolder(forward, report.getId());
		return new ResponseEntity<>(forward.getId(), HttpStatus.OK);
	}

	@PostMapping(value = "comment", consumes = "application/json")
	public ResponseEntity<?> comment(Principal principal, @Valid @RequestBody CommentDTO commentDTO, BindingResult validationResult)
			throws UserMissingException, ReportMissingException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		
		User commenter = userService.findOneByUsername(principal.getName());
		Report report = reportService.findOne(commentDTO.getReport());

		Comment comment = new Comment(commentDTO.getData(), commenter, report, LocalDateTime.now());
		comment = commentService.save(comment);

		return new ResponseEntity<>(comment.getId(), HttpStatus.OK);
	}

	@PostMapping(value = "bid", consumes = "application/json")
	public ResponseEntity<?> sendBid(Principal principal, @Valid @RequestBody BidDTO bidDTO, BindingResult validationResult) 
			throws UserMissingException, ReportMissingException {
		
		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		
		Company company = (Company) userService.findOneByUsername(principal.getName());
		Report report = reportService.findOne(bidDTO.getReport());

		Bid bid = new Bid(bidDTO.getDescription(), bidDTO.getPrice(), company, report, "OPEN");
		bidService.save(bid);
		
		return new ResponseEntity<Long>(bid.getId(), HttpStatus.OK);
	}

	@PostMapping(value = "acceptBid", consumes = "application/json")
	public ResponseEntity<?> acceptBid(Principal principal, @RequestBody AcceptBidDTO acceptBidDTO) 
			throws UserMissingException, BidMissingException, UserNotReportHolderException, InvalidStatusException {
		
		User user = userService.findOneByUsername(principal.getName());
		Bid bid = bidService.findOne(acceptBidDTO.getBid());

		bidService.acceptBid(user, bid);
		return new ResponseEntity<>("The bid is accepted.", HttpStatus.OK);
	}
	
	/**
	 * Handles UserNotResidentException that can happen when calling ReportService.create(report)
	 */
	@ExceptionHandler(UserNotResidentException.class)
	public ResponseEntity<String> userNotResidentException(final UserNotResidentException e) {
		return new ResponseEntity<String>("User with id: " + e.getUserId() + " is not resident in Building"
				+ " with id: " + e.getBuildingId(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles ReportMissingException that can happen when calling ReportService.findOne(reportId)
	 */
	@ExceptionHandler(ReportMissingException.class)
	public ResponseEntity<String> reportMissingException(final ReportMissingException e) {
		return new ResponseEntity<String>("Report with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserMissingException that can happen when calling UserService.findOne(userId)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		if(e.getId() != null)
			return new ResponseEntity<String>("User with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<String>("User with username: " + e.getUsername() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserNotReportHolderException that can happen when calling ForwardService.create(forward)
	 */
	@ExceptionHandler(UserNotReportHolderException.class)
	public ResponseEntity<String> userNotReportHolderException(final UserNotReportHolderException e) {
		return new ResponseEntity<String>("User with id: " + e.getUserId() + 
				" is not current holder of report with id: " + e.getReportId(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles BidMissingException that can happen when calling BidService.findOne(bidId)
	 */
	@ExceptionHandler(BidMissingException.class)
	public ResponseEntity<String> bidMissingException(final BidMissingException e) {
		return new ResponseEntity<String>("Bid with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles InvalidStatusException that can happen when calling BidService.acceptBid(user, bid)
	 */
	@ExceptionHandler(InvalidStatusException.class)
	public ResponseEntity<String> invalidStatusException(final InvalidStatusException e) {
		return new ResponseEntity<String>("Bid must have status 'OPEN'.", HttpStatus.NOT_FOUND);
	}
	
}
