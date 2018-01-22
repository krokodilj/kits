package com.timsedam.buildingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.ProposalMissingException;
import com.timsedam.buildingmanagement.exceptions.ReportNotAttachedToBuildingException;
import com.timsedam.buildingmanagement.exceptions.UserNotResidentException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Proposal;
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
	
	public Proposal create(Proposal proposal) throws UserNotResidentException, ReportNotAttachedToBuildingException {
		if(proposal.getAttachedReport() != null) {
			// user is not a resident or owner in the building he submitted a proposal to
			User proposer = proposal.getProposer();
			Report report = proposal.getAttachedReport();
			Building building = proposal.getBuilding();
			if(!buildingService.isResidentOrApartmentOwner(proposer, building))
				throw new UserNotResidentException(proposer.getId(), building.getId());
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
	
	public List<Proposal> findAllByBuildingId(Long buildingId) {
		return proposalRepository.findAllByBuildingId(buildingId);
	}

}
