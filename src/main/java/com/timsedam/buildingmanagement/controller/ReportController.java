package com.timsedam.buildingmanagement.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.BidAcceptDTO;
import com.timsedam.buildingmanagement.dto.request.BidSendDTO;
import com.timsedam.buildingmanagement.dto.request.CommentCreateDTO;
import com.timsedam.buildingmanagement.dto.request.ForwardCreateDTO;
import com.timsedam.buildingmanagement.dto.request.ReportCreateDTO;
import com.timsedam.buildingmanagement.dto.response.CommentDTO;
import com.timsedam.buildingmanagement.dto.response.ReportDTO;
import com.timsedam.buildingmanagement.exceptions.BidMissingException;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.exceptions.InvalidStatusException;
import com.timsedam.buildingmanagement.exceptions.ReportMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotReportHolderException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
import com.timsedam.buildingmanagement.mapper.CommentMapper;
import com.timsedam.buildingmanagement.mapper.ReportMapper;
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
	
	@Autowired
    private CommentMapper commentMapper;
	
	@Autowired
    private ReportMapper reportMapper;
   
	@PostMapping(consumes = "application/json", produces = "text/plain")
	public ResponseEntity<?> create(Principal principal, @Valid @RequestBody ReportCreateDTO reportDTO,
			BindingResult validationResult)
			throws BuildingMissingException, UserNotResidentException, UserMissingException, IOException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User reportSender = userService.findOneByUsername(principal.getName());
		Building building = buildingService.findOne(reportDTO.getBuilding());

		Report report = new Report(reportSender, "OPEN", reportDTO.getDescription(), building, reportDTO.getPhotos(),
				new ArrayList<Comment>(), null);

		Forward forward = new Forward(null, building.getManager(), report);

		report.setCurrentHolder(forward);

		report = reportService.create(report);
		forwardService.save(forward);

		return new ResponseEntity<String>(String.valueOf(report.getId()), HttpStatus.CREATED);

	}

	@PostMapping(value = "forward", consumes = "application/json")
	public ResponseEntity<?> forward(Principal principal, @Valid @RequestBody ForwardCreateDTO forwardCreateDTO,
			BindingResult validationResult)
			throws UserMissingException, ReportMissingException, UserNotReportHolderException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		User sender = userService.findOneByUsername(principal.getName());
		Report report = reportService.findOne(forwardCreateDTO.getReport());
		User receiver = userService.findOne(forwardCreateDTO.getTo());

		Forward forward = new Forward(sender, receiver, report);
		forward = forwardService.create(forward);

		reportService.setCurrentHolder(forward, report.getId());
		return new ResponseEntity<>(forward.getId(), HttpStatus.OK);
	}

	@PostMapping(value = "comment", consumes = "application/json")
	public ResponseEntity<?> comment(Principal principal, @Valid @RequestBody CommentCreateDTO commentDTO,
			BindingResult validationResult) throws UserMissingException, ReportMissingException {

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
	public ResponseEntity<?> sendBid(Principal principal, @Valid @RequestBody BidSendDTO bidSendDTO,
			BindingResult validationResult) throws UserMissingException, ReportMissingException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		Company company = (Company) userService.findOneByUsername(principal.getName());
		Report report = reportService.findOne(bidSendDTO.getReport());

		Bid bid = new Bid(bidSendDTO.getDescription(), bidSendDTO.getPrice(), company, report, "OPEN");
		bidService.save(bid);

		return new ResponseEntity<Long>(bid.getId(), HttpStatus.OK);
	}

	@PostMapping(value = "acceptBid", consumes = "application/json")
	public ResponseEntity<?> acceptBid(Principal principal, @RequestBody BidAcceptDTO bidAcceptDTO)
			throws UserMissingException, BidMissingException, UserNotReportHolderException, InvalidStatusException {

		User user = userService.findOneByUsername(principal.getName());
		Bid bid = bidService.findOne(bidAcceptDTO.getBid());

		bidService.acceptBid(user, bid);
		return new ResponseEntity<>("The bid is accepted.", HttpStatus.OK);
	}

	@GetMapping(value = "getComments/{reportId}", produces = "application/json")
    public ResponseEntity<?> getComments(@PathVariable Long reportId) throws ReportMissingException{
		List<Comment> comments = commentService.findAllByReport(reportId);
        
		List<CommentDTO> commentsDTO = commentMapper.toDto(comments);
        return new ResponseEntity<List<CommentDTO>>(commentsDTO, HttpStatus.OK);
    }
	
	@GetMapping(value = "getUserReports/{buildingId}", produces = "application/json")
    public ResponseEntity<?> getUserReports(@PathVariable Long buildingId) throws ReportMissingException{
		List<Report> reports = reportService.findAllByLocationId(buildingId);
        
		List<ReportDTO> reportsDTO = reportMapper.toDto(reports);
        return new ResponseEntity<List<ReportDTO>>(reportsDTO, HttpStatus.OK);
    }
	
	@GetMapping(value = "getReport/{reportId}", produces = "application/json")
    public ResponseEntity<?> getReport(@PathVariable Long reportId) throws ReportMissingException{
		Report report = reportService.findOne(reportId);
        
		ReportDTO reportDTO = reportMapper.toDto(report);
        return new ResponseEntity<ReportDTO>(reportDTO, HttpStatus.OK);
    }

	/**
	 * Handles UserNotResidentException that can happen when calling
	 * ReportService.create(report)
	 */
	@ExceptionHandler(UserNotResidentException.class)
	public ResponseEntity<String> userNotResidentException(final UserNotResidentException e) {
		return new ResponseEntity<String>(
				"User with id: " + e.getUserId() + " is not resident in Building" + " with id: " + e.getBuildingId(),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * Handles ReportMissingException that can happen when calling
	 * ReportService.findOne(reportId)
	 */
	@ExceptionHandler(ReportMissingException.class)
	public ResponseEntity<String> reportMissingException(final ReportMissingException e) {
		return new ResponseEntity<String>("Report with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles UserMissingException that can happen when calling
	 * UserService.findOne(userId)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		if (e.getId() != null)
			return new ResponseEntity<String>("User with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<String>("User with username: " + e.getUsername() + " doesn't exist.",
					HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles UserNotReportHolderException that can happen when calling
	 * ForwardService.create(forward)
	 */
	@ExceptionHandler(UserNotReportHolderException.class)
	public ResponseEntity<String> userNotReportHolderException(final UserNotReportHolderException e) {
		return new ResponseEntity<String>(
				"User with id: " + e.getUserId() + " is not current holder of report with id: " + e.getReportId(),
				HttpStatus.UNPROCESSABLE_ENTITY);
	}

	/**
	 * Handles BidMissingException that can happen when calling
	 * BidService.findOne(bidId)
	 */
	@ExceptionHandler(BidMissingException.class)
	public ResponseEntity<String> bidMissingException(final BidMissingException e) {
		return new ResponseEntity<String>("Bid with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles InvalidStatusException that can happen when calling
	 * BidService.acceptBid(user, bid)
	 */
	@ExceptionHandler(InvalidStatusException.class)
	public ResponseEntity<String> invalidStatusException(final InvalidStatusException e) {
		return new ResponseEntity<String>("Bid must have status 'OPEN'.", HttpStatus.UNPROCESSABLE_ENTITY);
	}

}
