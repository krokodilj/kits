package com.timsedam.buildingmanagement.exceptions;

public class UserAlreadyVotedException extends Exception {

	private Long voterId;
	private Long proposalId;

	public UserAlreadyVotedException(Long voterId, Long proposalId) {
		super();
		this.voterId = voterId;
		this.proposalId = proposalId;
	}

	public Long getVoterId() {
		return voterId;
	}

	public void setVoterId(Long voterId) {
		this.voterId = voterId;
	}

	public Long getProposalId() {
		return proposalId;
	}

	public void setProposalId(Long proposalId) {
		this.proposalId = proposalId;
	}

	@Override
	public String toString() {
		return "UserAlreadyVotedException [voterId=" + voterId + ", proposalId=" + proposalId + "]";
	}

}
