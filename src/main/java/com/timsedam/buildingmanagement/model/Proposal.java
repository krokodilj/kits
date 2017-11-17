package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Proposal {
	
	@Id
	@GeneratedValue
	private Long id;
	private String content;
	private ProposalStatus status;
	@ManyToOne
	private User proposer;
	@ManyToOne
	private Meeting meeting;
	private LocalDateTime suggestionTime;
	@ManyToOne
	private List<ProposalVote> votes;
	
	public Proposal() {}

	public Proposal(String content, ProposalStatus status, User proposer, Meeting meeting, LocalDateTime suggestionTime,
			List<ProposalVote> votes) {
		super();
		this.content = content;
		this.status = status;
		this.proposer = proposer;
		this.meeting = meeting;
		this.suggestionTime = suggestionTime;
		this.votes = votes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProposalStatus getStatus() {
		return status;
	}

	public void setStatus(ProposalStatus status) {
		this.status = status;
	}

	public User getProposer() {
		return proposer;
	}

	public void setProposer(User proposer) {
		this.proposer = proposer;
	}

	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
	}

	public LocalDateTime getSuggestionTime() {
		return suggestionTime;
	}

	public void setSuggestionTime(LocalDateTime suggestionTime) {
		this.suggestionTime = suggestionTime;
	}

	public List<ProposalVote> getVotes() {
		return votes;
	}

	public void setVotes(List<ProposalVote> votes) {
		this.votes = votes;
	}

	@Override
	public String toString() {
		return "Proposal [id=" + id + ", content=" + content + ", status=" + status + ", proposer=" + proposer
				+ ", meeting=" + meeting + ", suggestionTime=" + suggestionTime + ", votes=" + votes + "]";
	}

}
