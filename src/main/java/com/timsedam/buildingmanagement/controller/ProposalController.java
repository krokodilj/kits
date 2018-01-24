package com.timsedam.buildingmanagement.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.ProposalCreateDTO;
import com.timsedam.buildingmanagement.dto.response.ProposalDTO;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.exceptions.ProposalMissingException;
import com.timsedam.buildingmanagement.exceptions.ReportMissingException;
import com.timsedam.buildingmanagement.exceptions.ReportNotAttachedToBuildingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
import com.timsedam.buildingmanagement.mapper.ProposalMapper;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.ProposalService;
import com.timsedam.buildingmanagement.service.ReportService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/proposals")
public class ProposalController {
	
	@Autowired
	private ProposalService proposalService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private ProposalMapper proposalMapper;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody ProposalCreateDTO proposalCreateDTO, BindingResult validationResult, Principal principal) 
			throws UserMissingException, BuildingMissingException, ReportMissingException, UserNotResidentException, ReportNotAttachedToBuildingException {		
		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		else {
			User requestSender = userService.findOneByUsername(principal.getName());
			Building building = buildingService.findOne(proposalCreateDTO.getBuildingId());
			Proposal proposal = null;
			
			if(proposalCreateDTO.getAttachedReport() != null) {
				Report attachedReport = reportService.findOne(proposalCreateDTO.getAttachedReport());
				proposal = proposalMapper.toModel(proposalCreateDTO, requestSender, building, attachedReport);
				
			}
			else {
				proposal = proposalMapper.toModel(proposalCreateDTO, requestSender, building);
			}
			
			proposal = proposalService.create(proposal);
			return new ResponseEntity<Long>(proposal.getId(), HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "{proposalId}", produces = "application/json")
	public ResponseEntity<ProposalDTO> get(@PathVariable Long proposalId) 
			throws ClassNotFoundException, ProposalMissingException {
		Proposal proposal = proposalService.findOne(proposalId);
		ProposalDTO responseData = proposalMapper.toDto(proposal);
		return new ResponseEntity<ProposalDTO>(responseData, HttpStatus.OK);
	}
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ProposalDTO>> getAllByBuildingId(@RequestParam Long buildingId) {
		List<Proposal> proposals = proposalService.findAllByBuildingId(buildingId);
		List<ProposalDTO> dtos = proposalMapper.toDto(proposals);
		return new ResponseEntity<List<ProposalDTO>>(dtos, HttpStatus.OK);
	}
	
	/**
	 * Handles UserMissingException that can happen when calling UserServce.findByName(username)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		return new ResponseEntity<String>("User with username: " + e.getUsername() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles BuildingMissingException that can happen when calling BuildingService.findOne(buildingId)
	 */
	@ExceptionHandler(BuildingMissingException.class)
	public ResponseEntity<String> buildingMissingException(final BuildingMissingException e) {
		return new ResponseEntity<String>("Building with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles ReportMissingException that can happen when calling ReportService.findOne(reportId)
	 */
	@ExceptionHandler(ReportMissingException.class)
	public ResponseEntity<String> reportMissingException(final ReportMissingException e) {
		return new ResponseEntity<String>("Report with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserNotResidentException that can happen when calling ProposalService.create(proposal)
	 */
	@ExceptionHandler(UserNotResidentException.class)
	public ResponseEntity<String> userNotResidentException(final UserNotResidentException e) {
		return new ResponseEntity<String>("User with id: " + e.getUserId() + " is not a Resident or Owner"
				+ " in Building with id: " + e.getBuildingId(), HttpStatus.UNPROCESSABLE_ENTITY);
	} 
	
	/**
	 * Handles ReportNotAttachedToBuildingException that can happen when calling ProposalService.create(proposal)
	 */
	@ExceptionHandler(ReportNotAttachedToBuildingException.class)
	public ResponseEntity<String> reportNotAttachedToBuildingException(final ReportNotAttachedToBuildingException e) {
		return new ResponseEntity<String>("Report with id: " + e.getReportId() + " is not attached to"
				+ " Building with id: " + e.getBuildingId(), HttpStatus.UNPROCESSABLE_ENTITY);
	} 
	
	/**
	 * Handles ProposalMissingException that can happen when calling ProposalService.findOne(proposalId)
	 */
	@ExceptionHandler(ProposalMissingException.class)
	public ResponseEntity<String> proposalMissingException(final ProposalMissingException e) {
		return new ResponseEntity<String>("Proposal with id: " + e.getProposalId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	} 
	
}
