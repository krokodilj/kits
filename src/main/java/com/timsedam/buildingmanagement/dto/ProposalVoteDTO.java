package com.timsedam.buildingmanagement.dto;

import com.timsedam.buildingmanagement.model.ProposalVoteValue;

public class ProposalVoteDTO {
	
	private Long id;
	private Long voter;
	private ProposalVoteValue vote;
	
	public ProposalVoteDTO() {
		super();
	}

	public ProposalVoteDTO(Long voter, ProposalVoteValue vote) {
		super();
		this.voter = voter;
		this.vote = vote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVoter() {
		return voter;
	}

	public void setVoter(Long voter) {
		this.voter = voter;
	}

	public ProposalVoteValue getVote() {
		return vote;
	}

	public void setVote(ProposalVoteValue vote) {
		this.vote = vote;
	}

	@Override
	public String toString() {
		return "ProposalVoteDTO [id=" + id + ", voter=" + voter + ", vote=" + vote + "]";
	}

}
