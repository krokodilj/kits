package com.timsedam.buildingmanagement.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.timsedam.buildingmanagement.model.ProposalVote;

public interface ProposalVoteRepository extends JpaRepository<ProposalVote, Long> {
	
	@Query("SELECT pv FROM Proposal as pr join pr.votes as pv where pr.id = ?1")
	ArrayList<ProposalVote> findAllByProposalId(Long proposalId);

}
