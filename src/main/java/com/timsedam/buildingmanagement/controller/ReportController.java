package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.AcceptBidDTO;
import com.timsedam.buildingmanagement.dto.BidDTO;
import com.timsedam.buildingmanagement.dto.CommentDTO;
import com.timsedam.buildingmanagement.dto.CreateReportDTO;
import com.timsedam.buildingmanagement.dto.ForwardDTO;
import com.timsedam.buildingmanagement.model.Bid;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.Forward;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.Resident;
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

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "create", consumes = "application/json")
	public ResponseEntity create(Principal principal, @RequestBody CreateReportDTO reportDTO) {

		Resident sender = (Resident) userService.findOneByUsername(principal.getName());
		Building building = buildingService.findOneById(reportDTO.getBuilding());

		if (!sender.isResident(building)) {
			return new ResponseEntity<>("Sender isn't resident of building!", HttpStatus.CONFLICT);
		}

		Report report = new Report(sender, "OPEN", reportDTO.getDescription(), building, reportDTO.getPhotos(),
				new ArrayList<Comment>(), null);
		Forward forward = new Forward(null, building.getManager(), report);

		report.setCurrentHolder(forward);
		report = reportService.save(report);

		return new ResponseEntity<>(report.getId(), HttpStatus.CREATED);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "forward", consumes = "application/json")
	public ResponseEntity forward(Principal principal, @RequestBody ForwardDTO forwardDTO) {

		User forwared = userService.findOneByUsername(principal.getName());
		Report report = reportService.findOne(forwardDTO.getReport());

		if (report == null)
			return new ResponseEntity<>("Report doesn't exist.", HttpStatus.NOT_FOUND);

		User to = userService.findOne(forwardDTO.getTo());
		if (to == null)
			return new ResponseEntity<>("Forwarded user doesn't exist.", HttpStatus.NOT_FOUND);

		if (forwared.getId() != report.getCurrentHolder().getForwardedTo().getId()) {
			return new ResponseEntity<>("You can't forward report because you are not current holder.",
					HttpStatus.CONFLICT);
		}

		Forward forward = new Forward(forwared, to, report);
		forward = forwardService.save(forward);

		reportService.setCurrentHolder(forward, report.getId());
		return new ResponseEntity<>("The report was successfully forwarded.", HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "comment", consumes = "application/json")
	public ResponseEntity comment(Principal principal, @RequestBody CommentDTO commentDTO) {

		User commenter = userService.findOneByUsername(principal.getName());
		Report report = reportService.findOne(commentDTO.getReport());

		if (report == null)
			return new ResponseEntity<>("Report doesn't exist.", HttpStatus.NOT_FOUND);
		;

		if (commentDTO.getData().isEmpty())
			return new ResponseEntity<>("Comment is empty.", HttpStatus.BAD_REQUEST);

		Comment comment = new Comment(commentDTO.getData(), commenter, report, LocalDateTime.now());
		comment = commentService.save(comment);

		return new ResponseEntity<>(comment.getId(), HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "bid", consumes = "application/json")
	public ResponseEntity sendBid(Principal principal, @RequestBody BidDTO bidDTO) {
		Company company = (Company) userService.findOneByUsername(principal.getName());

		Report report = reportService.findOne(bidDTO.getReport());
		if (report == null)
			return new ResponseEntity<>("Report doesn't exist.", HttpStatus.NOT_FOUND);

		Bid bid = new Bid(bidDTO.getDescription(), bidDTO.getPrice(), company, report, "OPEN");

		bidService.save(bid);
		return new ResponseEntity<>("The bid was successfully sent.", HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "acceptBid", consumes = "application/json")
	public ResponseEntity acceptBid(Principal principal, @RequestBody AcceptBidDTO acceptBidDTO) {
		User user = userService.findOneByUsername(principal.getName());
		Bid bid = bidService.findOne(acceptBidDTO.getBid());
		if (bid == null)
			return new ResponseEntity<>("Bid doesn't exist.", HttpStatus.NOT_FOUND);

		if (user.getId() != bid.getReport().getCurrentHolder().getForwardedTo().getId())
			return new ResponseEntity<>("You can't accept bid because you are not current holder!",
					HttpStatus.FORBIDDEN);
		
		if(!bid.getStatus().equals("OPEN"))
			return new ResponseEntity<>("You can't accept bid because bid status isn't OPEN!",
					HttpStatus.CONFLICT);

		bidService.setStatus("DECLINED", bid.getReport());
		bidService.setStatus("ACCEPTED", bid.getId());
		
		reportService.setStatus("CLOSED", bid.getReport().getId());
		return new ResponseEntity<>("The bid is accepted.", HttpStatus.OK);
	}
}
