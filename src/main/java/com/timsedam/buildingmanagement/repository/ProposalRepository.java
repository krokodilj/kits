package com.timsedam.buildingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.ProposalStatus;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
	
	public List<Proposal> findAllByBuildingIdAndStatus(Long buildingId, ProposalStatus status);

}
