package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ProposalVote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private User voter;
	@ManyToOne
	private Proposal proposal;
	private ProposalVoteValue vote;
	
	public ProposalVote() {}

	public ProposalVote(User voter, Proposal proposal, ProposalVoteValue vote) {
		super();
		this.voter = voter;
		this.proposal = proposal;
		this.vote = vote;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getVoter() {
		return voter;
	}

	public void setVoter(User voter) {
		this.voter = voter;
	}

	public Proposal getProposal() {
		return proposal;
	}

	public void setProposal(Proposal proposal) {
		this.proposal = proposal;
	}

	public ProposalVoteValue getVote() {
		return vote;
	}

	public void setVote(ProposalVoteValue vote) {
		this.vote = vote;
	}

	@Override
	public String toString() {
		return "ProposalVote [id=" + id + ", voter=" + voter + ", proposal=" + proposal + ", vote=" + vote + "]";
	}

}
