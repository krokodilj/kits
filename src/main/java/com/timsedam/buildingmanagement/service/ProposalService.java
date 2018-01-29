package com.timsedam.buildingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.ProposalMissingException;
import com.timsedam.buildingmanagement.exceptions.ReportNotAttachedToBuildingException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentOrApartmentOwnerException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.ProposalStatus;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.ProposalRepository;

@Service
public class ProposalService {
	
	
	@Autowired
	private ProposalRepository proposalRepository;

	@Autowired
	private BuildingService buildingService;
	
	@Autowired
	private ReportService reportService;
	
	public void save(Proposal proposal) {
		proposalRepository.save(proposal);
	}
	
	public Proposal create(Proposal proposal) throws UserNotResidentOrApartmentOwnerException, ReportNotAttachedToBuildingException {
		if(proposal.getAttachedReport() != null) {
			User proposer = proposal.getProposer();
			Report report = proposal.getAttachedReport();
			Building building = proposal.getBuilding();
			if(!buildingService.isResidentOrApartmentOwner(proposer, building))
				throw new UserNotResidentOrApartmentOwnerException(proposer.getId(), building.getId());
			if(!reportService.isAttachedToBuilding(report, building))
				throw new ReportNotAttachedToBuildingException(report.getId(), building.getId());
		}
		return proposalRepository.save(proposal);
	}
	
	public Proposal findOne(Long id) throws ProposalMissingException {
		Proposal proposal = proposalRepository.findOne(id);
		if(proposal == null)
			throw new ProposalMissingException(id);
		else
			return proposal;
	}
	
	public List<Proposal> findAllByBuildingAndProposalStatus(Long buildingId, ProposalStatus proposalStatus) {
		return proposalRepository.findAllByBuildingIdAndStatus(buildingId, proposalStatus);
	}

}
