package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.UserNotApartmentOwnerException;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.ProposalVote;
import com.timsedam.buildingmanagement.repository.ProposalRepository;
import com.timsedam.buildingmanagement.repository.ProposalVoteRepository;

@Service
public class ProposalVoteService {
	
	@Autowired
	private ProposalVoteRepository proposalVoteRepository;
	
	@Autowired
	private ProposalRepository proposalRepository;
	
	@Autowired
	private BuildingService buildingService;
	
	public ProposalVote create(ProposalVote vote, Proposal proposal) throws UserNotApartmentOwnerException {
		if(!buildingService.isApartmentOwner(vote.getVoter(), proposal.getBuilding()))
			throw new UserNotApartmentOwnerException(vote.getVoter().getId(), proposal.getBuilding().getId());
		
		vote = proposalVoteRepository.save(vote);
		proposal.getVotes().add(vote);
		proposalRepository.save(proposal);
		return vote;
	}

}
