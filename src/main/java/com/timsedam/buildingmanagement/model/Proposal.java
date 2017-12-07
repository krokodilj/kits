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
	private List<ProposalVote> discussOnMeetingVotes;
	@OneToMany
	private List<ProposalVote> onMeetingVotes;
	@ManyToOne
	private Report attachedReport;
	
	public Proposal() {
		super();
	}

	public Proposal(String content, ProposalStatus status, User proposer, Meeting meeting, LocalDateTime suggestedAt,
			List<ProposalVote> discussOnMeetingVotes, List<ProposalVote> onMeetingVotes, Report attachedReport) {
		super();
		this.content = content;
		this.status = status;
		this.proposer = proposer;
		this.meeting = meeting;
		this.suggestedAt = suggestedAt;
		this.discussOnMeetingVotes = discussOnMeetingVotes;
		this.onMeetingVotes = onMeetingVotes;
		this.attachedReport = attachedReport;
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

	public List<ProposalVote> getDiscussOnMeetingVotes() {
		return discussOnMeetingVotes;
	}

	public void setDiscussOnMeetingVotes(List<ProposalVote> discussOnMeetingVotes) {
		this.discussOnMeetingVotes = discussOnMeetingVotes;
	}

	public List<ProposalVote> getOnMeetingVotes() {
		return onMeetingVotes;
	}

	public void setOnMeetingVotes(List<ProposalVote> onMeetingVotes) {
		this.onMeetingVotes = onMeetingVotes;
	}

	public Report getAttachedReport() {
		return attachedReport;
	}

	public void setAttachedReport(Report attachedReport) {
		this.attachedReport = attachedReport;
	}

	@Override
	public String toString() {
		return "Proposal [id=" + id + ", content=" + content + ", status=" + status + ", proposer=" + proposer
				+ ", meeting=" + meeting + ", suggestedAt=" + suggestedAt + ", discussOnMeetingVotes="
				+ discussOnMeetingVotes + ", onMeetingVotes=" + onMeetingVotes + ", attachedReport=" + attachedReport
				+ "]";
	}
	
}
