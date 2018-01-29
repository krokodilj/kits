package com.timsedam.buildingmanagement.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.UserAlreadyVotedException;
import com.timsedam.buildingmanagement.exceptions.UserNotApartmentOwnerException;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.ProposalVote;
import com.timsedam.buildingmanagement.model.User;
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
	
	public ProposalVote create(ProposalVote vote, Proposal proposal) throws UserNotApartmentOwnerException, UserAlreadyVotedException {
		if(!(buildingService.isManager(proposal.getBuilding(), vote.getVoter()) ||
			 buildingService.isResidentOrApartmentOwner(vote.getVoter(), proposal.getBuilding())))
			throw new UserNotApartmentOwnerException(vote.getVoter().getId(), proposal.getBuilding().getId());
		if(alreadyVoted(vote.getVoter(), proposal))
			throw new UserAlreadyVotedException(vote.getVoter().getId(), proposal.getId());
		vote = proposalVoteRepository.save(vote);
		proposal.getVotes().add(vote);
		proposalRepository.save(proposal);
		return vote;
	}
	
	public ArrayList<ProposalVote> findAllByProposalId(Long proposalId) {
		return proposalVoteRepository.findAllByProposalId(proposalId);
	}
	
	private boolean alreadyVoted(User voter, Proposal proposal) {
	  for(ProposalVote vote : proposal.getVotes()) {
	    if(vote.getVoter().getId() == voter.getId())
	      return true;
	  }
	  return false;
	}
	
}
