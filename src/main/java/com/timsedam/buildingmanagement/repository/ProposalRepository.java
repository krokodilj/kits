package com.timsedam.buildingmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
	
	public List<Proposal> findAllByBuildingId(Long buildingId);

}
