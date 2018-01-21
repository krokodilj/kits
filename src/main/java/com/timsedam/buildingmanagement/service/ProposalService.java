package com.timsedam.buildingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.repository.ProposalRepository;

@Service
public class ProposalService {
	
	@Autowired
	private ProposalRepository proposalRepository;
	
	public void save(Proposal proposal) {
		proposalRepository.save(proposal);
	}
	
	public Proposal get(Long id) {
		return proposalRepository.getOne(id);
	}
	
	public List<Proposal> findAllByBuildingId(Long buildingId) {
		return proposalRepository.findAllByBuildingId(buildingId);
	}

}
