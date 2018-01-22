package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.ProposalCreateDTO;
import com.timsedam.buildingmanagement.dto.response.ProposalDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Proposal;
import com.timsedam.buildingmanagement.model.ProposalStatus;
import com.timsedam.buildingmanagement.model.ProposalVote;
import com.timsedam.buildingmanagement.model.Report;
import com.timsedam.buildingmanagement.model.User;

@Component
public class ProposalMapper {
	
	public Proposal toModel(ProposalCreateDTO proposalCreateDTO,
			User proposer, Building building, Report attachedReport) {
		
		Proposal proposal = new Proposal();
		proposal.setContent(proposalCreateDTO.getContent());
		proposal.setStatus(ProposalStatus.OPEN);
		proposal.setProposer(proposer);
		proposal.setMeeting(null);
		proposal.setSuggestedAt(proposalCreateDTO.getSuggestedAt());
		proposal.setVotes(new ArrayList<ProposalVote>());
		proposal.setAttachedReport(attachedReport);
		proposal.setBuilding(building);
		
		return proposal;
	}
	
	public Proposal toModel(ProposalCreateDTO proposalCreateDTO, User proposer, Building building) {
		
		Proposal proposal = new Proposal();
		proposal.setContent(proposalCreateDTO.getContent());
		proposal.setStatus(ProposalStatus.OPEN);
		proposal.setProposer(proposer);
		proposal.setMeeting(null);
		proposal.setSuggestedAt(proposalCreateDTO.getSuggestedAt());
		proposal.setVotes(new ArrayList<ProposalVote>());
		proposal.setAttachedReport(null);
		proposal.setBuilding(building);
				
		return proposal;
	}
	
	public ProposalDTO toDto(Proposal proposal) {
		
		ProposalDTO proposalDTO = new ProposalDTO();
		proposalDTO.setId(proposal.getId());
		proposalDTO.setContent(proposal.getContent());
		proposalDTO.setStatus(proposal.getStatus());
		proposalDTO.setProposer(proposal.getProposer().getId());
		
		if(proposal.getMeeting() != null)
			proposalDTO.setMeeting(proposal.getMeeting().getId());
			

		proposalDTO.setSuggestedAt(proposal.getSuggestedAt());
		
		List<Long> votes = new ArrayList<Long>();
		for(ProposalVote vote : proposal.getVotes()) {
			votes.add(vote.getId());
		}
		proposalDTO.setVotes(votes);
		
		if(proposal.getAttachedReport() != null)
			proposalDTO.setAttachedReport(proposal.getAttachedReport().getId());
				
		return proposalDTO;
	}
	
	public ArrayList<ProposalDTO> toDto(List<Proposal> proposals) {
		ArrayList<ProposalDTO> dtos = new ArrayList<>();
		for(Proposal proposal : proposals) {
			dtos.add(toDto(proposal));
		}
		return dtos;
	}

}
