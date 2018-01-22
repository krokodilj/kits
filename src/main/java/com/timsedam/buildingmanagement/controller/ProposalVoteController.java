package com.timsedam.buildingmanagement.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.ProposalVoteCastDTO;
import com.timsedam.buildingmanagement.dto.response.ProposalVoteDTO;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
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
	public ResponseEntity<ProposalVoteDTO> cast(@Valid @RequestBody ProposalVoteCastDTO proposalVoteCastDTO,
			BindingResult validationResult, Principal principal) throws ClassNotFoundException, UserMissingException {
		
		User requestSender = userService.findOneByUsername(principal.getName());
		
		Proposal proposal = null;
		
		if(proposalVoteCastDTO.getProposalId() != null)
			proposal = proposalService.get(proposalVoteCastDTO.getProposalId());
		
		if(validationResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(!userService.isApartmentOwnerInBuilding(requestSender.getId(), proposal.getBuilding().getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			ProposalVote proposalVote = proposalVoteMapper.toModel(proposalVoteCastDTO, requestSender);
			proposalVoteService.save(proposalVote);
			
			ProposalVoteDTO responseData = proposalVoteMapper.toDto(proposalVote);			
			return new ResponseEntity<ProposalVoteDTO>(responseData, HttpStatus.CREATED);
		}
		
	}

}
