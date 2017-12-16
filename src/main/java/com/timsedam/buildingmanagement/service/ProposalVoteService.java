package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.ProposalVote;
import com.timsedam.buildingmanagement.repository.ProposalVoteRepository;

@Service
public class ProposalVoteService {
	
	@Autowired
	private ProposalVoteRepository proposalVoteRepository;
	
	public void save(ProposalVote proposalVote) {
		proposalVoteRepository.save(proposalVote);
	}

}
