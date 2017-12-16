package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.ProposalVote;

public interface ProposalVoteRepository extends JpaRepository<ProposalVote, Long> {
	
	

}
