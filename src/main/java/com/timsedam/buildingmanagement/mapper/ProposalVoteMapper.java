package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.ProposalVoteCastDTO;
import com.timsedam.buildingmanagement.dto.response.ProposalVoteDTO;
import com.timsedam.buildingmanagement.model.ProposalVote;
import com.timsedam.buildingmanagement.model.User;

@Component
public class ProposalVoteMapper {
	
	public ProposalVote toModel(ProposalVoteCastDTO proposalVoteCastDTO, User voter) {
		
		ProposalVote proposalVote = new ProposalVote();
		proposalVote.setVote(proposalVoteCastDTO.getValue());
		proposalVote.setVoter(voter);
		
		return proposalVote;
	}
	
	public ProposalVoteDTO toDto(ProposalVote proposalVote) {
		ProposalVoteDTO proposalVoteDTO = new ProposalVoteDTO();
		proposalVoteDTO.setId(proposalVote.getId());
		proposalVoteDTO.setVoter(proposalVote.getVoter().getId());
		proposalVoteDTO.setVote(proposalVote.getVote());
		
		return proposalVoteDTO;
	}
	
	public ArrayList<ProposalVoteDTO> toDto(ArrayList<ProposalVote> votes) {
		ArrayList<ProposalVoteDTO> dtos = new ArrayList<ProposalVoteDTO>();
		for(ProposalVote vote : votes) {
			dtos.add(toDto(vote));
		}
		return dtos;
	}

}
