package com.timsedam.buildingmanagement.dto.request;

import javax.validation.constraints.NotNull;

import com.timsedam.buildingmanagement.model.ProposalVoteValue;

public class ProposalVoteCastDTO {
	
	@NotNull(message = "'value' not provided")
	private ProposalVoteValue value;
	
	@NotNull(message = "'proposalId' not provided")
	private Long proposalId;
	
	public ProposalVoteCastDTO() {
		super();
	}

	public ProposalVoteCastDTO(ProposalVoteValue value, Long proposalId) {
		super();
		this.value = value;
		this.proposalId = proposalId;
	}
	
	public ProposalVoteCastDTO(ProposalVoteCastDTO proposalVoteCastDTO) {
		this.value = proposalVoteCastDTO.getValue();
		this.proposalId = proposalVoteCastDTO.getProposalId();
	}

	public ProposalVoteValue getValue() {
		return value;
	}

	public void setValue(ProposalVoteValue value) {
		this.value = value;
	}

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	@Override
	public String toString() {
		return "ProposalVoteCastDTO [value=" + value + ", proposalId=" + proposalId + "]";
	}

}
