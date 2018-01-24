package com.timsedam.buildingmanagement.controller;

import java.security.Principal;

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

import com.timsedam.buildingmanagement.dto.request.ProposalVoteCastDTO;
import com.timsedam.buildingmanagement.exceptions.ProposalMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.exceptions.UserNotApartmentOwnerException;
import com.timsedam.buildingmanagement.mapper.ProposalVoteMapper;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.ProposalVote;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.ProposalService;
import com.timsedam.buildingmanagement.service.ProposalVoteService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/proposal_votes")
public class ProposalVoteController {
	
	@Autowired
	private ProposalVoteService proposalVoteService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProposalService proposalService;
	
	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private ProposalVoteMapper proposalVoteMapper;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> cast(@Valid @RequestBody ProposalVoteCastDTO proposalVoteCastDTO, 
			BindingResult validationResult, Principal principal) 
					throws UserMissingException, ProposalMissingException, UserNotApartmentOwnerException {
		
		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		
		User requestSender = userService.findOneByUsername(principal.getName());
		Proposal proposal = proposalService.findOne(proposalVoteCastDTO.getProposalId());
		
		ProposalVote proposalVote = proposalVoteMapper.toModel(proposalVoteCastDTO, requestSender);
		proposalVote = proposalVoteService.create(proposalVote, proposal);
		
		return new ResponseEntity<Long>(proposalVote.getId(), HttpStatus.CREATED);
	}
	
	/**
	 * Handles UserMissingException that can happen when calling UserServce.findByName(username)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		return new ResponseEntity<String>("User with username: " + e.getUsername() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles ProposalMissingException that can happen when calling ProposalService.findOne(proposalId)
	 */
	@ExceptionHandler(ProposalMissingException.class)
	public ResponseEntity<String> proposalMissingException(final ProposalMissingException e) {
		return new ResponseEntity<String>("Proposal with id: " + e.getProposalId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserNotApartmentOwnerException that can happen when calling ProposalVoteService.create(ProposalVote)
	 */
	@ExceptionHandler(UserNotApartmentOwnerException.class)
	public ResponseEntity<String> userNotApartmentOwnerException(final UserNotApartmentOwnerException e) {
		return new ResponseEntity<String>("User with id: " + e.getUserId() + " is not an ApartmentOwner "
				+ "in Building with id: " + e.getBuildingId(), HttpStatus.NOT_FOUND);
	}

}
