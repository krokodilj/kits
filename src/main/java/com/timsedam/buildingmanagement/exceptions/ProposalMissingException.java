package com.timsedam.buildingmanagement.exceptions;

public class ProposalMissingException extends Exception {
	
	private Long proposalId;

	public ProposalMissingException(Long proposalId) {
		super();
		this.proposalId = proposalId;
	}

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}
	
}
