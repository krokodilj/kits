package com.timsedam.buildingmanagement.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.timsedam.buildingmanagement.model.ProposalStatus;

public class ProposalDTO {
	
	private Long id;
	private String content;
	private ProposalStatus status;
	private Long proposer;
	private Long meeting;
	private LocalDateTime suggestedAt;
	private List<Long> votes;
	private Long attachedReport;
	
	public ProposalDTO() {
		super();
	}

	public ProposalDTO(String content, ProposalStatus status, Long proposer, Long meeting, LocalDateTime suggestedAt,
			List<Long> votes, Long attachedReport) {
		super();
		this.content = content;
		this.status = status;
		this.proposer = proposer;
		this.meeting = meeting;
		this.suggestedAt = suggestedAt;
		this.votes = votes;
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

	public Long getProposer() {
		return proposer;
	}

	public void setProposer(Long proposer) {
		this.proposer = proposer;
	}

	public Long getMeeting() {
		return meeting;
	}

	public void setMeeting(Long meeting) {
		this.meeting = meeting;
	}

	public LocalDateTime getSuggestedAt() {
		return suggestedAt;
	}

	public void setSuggestedAt(LocalDateTime suggestedAt) {
		this.suggestedAt = suggestedAt;
	}

	public List<Long> getVotes() {
		return votes;
	}

	public void setVotes(List<Long> votes) {
		this.votes = votes;
	}

	public Long getAttachedReport() {
		return attachedReport;
	}

	public void setAttachedReport(Long attachedReport) {
		this.attachedReport = attachedReport;
	}

	@Override
	public String toString() {
		return "ProposalDTO [id=" + id + ", content=" + content + ", status=" + status + ", proposer=" + proposer
				+ ", meeting=" + meeting + ", suggestedAt=" + suggestedAt + ", votes=" + votes + ", attachedReport="
				+ attachedReport + "]";
	}
	
}
