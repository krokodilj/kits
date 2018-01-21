package com.timsedam.buildingmanagement.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Proposal {
	
	@Id
	@GeneratedValue
	private Long id;
	private String content;
	@Enumerated(EnumType.STRING)
	private ProposalStatus status;
	@ManyToOne
	private User proposer;
	@ManyToOne
	private Meeting meeting;
	private LocalDateTime suggestedAt;
	@OneToMany
	private List<ProposalVote> votes;
	@ManyToOne
	private Report attachedReport;
	@ManyToOne
	private Building building;
	
	public Proposal() {
		super();
	}

	public Proposal(String content, ProposalStatus status, User proposer, Meeting meeting, LocalDateTime suggestedAt,
			List<ProposalVote> votes, Report attachedReport, Building building) {
		super();
		this.content = content;
		this.status = status;
		this.proposer = proposer;
		this.meeting = meeting;
		this.suggestedAt = suggestedAt;
		this.votes = votes;
		this.attachedReport = attachedReport;
		this.building = building;
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

	public LocalDateTime getSuggestedAt() {
		return suggestedAt;
	}

	public void setSuggestedAt(LocalDateTime suggestedAt) {
		this.suggestedAt = suggestedAt;
	}

	public List<ProposalVote> getVotes() {
		return votes;
	}

	public void setVotes(List<ProposalVote> votes) {
		this.votes = votes;
	}

	public Report getAttachedReport() {
		return attachedReport;
	}

	public void setAttachedReport(Report attachedReport) {
		this.attachedReport = attachedReport;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Override
	public String toString() {
		return "Proposal [id=" + id + ", content=" + content + ", status=" + status + ", proposer=" + proposer
				+ ", meeting=" + meeting + ", suggestedAt=" + suggestedAt + ", votes=" + votes + ", attachedReport="
				+ attachedReport + ", building=" + building + "]";
	}
	
}
