package com.timsedam.buildingmanagement.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class ProposalCreateDTO {
	
	@NotNull
	private String content;
	@NotNull
	private LocalDateTime suggestedAt;
	private Long attachedReport;
	private Long buildingId;
	
	public ProposalCreateDTO() {
		super();
	}
	
	public ProposalCreateDTO(String content, LocalDateTime suggestedAt, Long attachedReport, Long buildingId) {
		super();
		this.content = content;
		this.suggestedAt = suggestedAt;
		this.attachedReport = attachedReport;
		this.buildingId = buildingId;
	}

	public ProposalCreateDTO(ProposalCreateDTO proposalCreateDTO) {
		this.content = new String(proposalCreateDTO.getContent());
		this.suggestedAt = proposalCreateDTO.getSuggestedAt();
		this.attachedReport = proposalCreateDTO.getAttachedReport();
		this.buildingId = proposalCreateDTO.getBuildingId();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getSuggestedAt() {
		return suggestedAt;
	}

	public void setSuggestedAt(LocalDateTime suggestedAt) {
		this.suggestedAt = suggestedAt;
	}

	public Long getAttachedReport() {
		return attachedReport;
	}

	public void setAttachedReport(Long attachedReport) {
		this.attachedReport = attachedReport;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	@Override
	public String toString() {
		return "ProposalCreateDTO [content=" + content + ", suggestedAt=" + suggestedAt + ", attachedReport="
				+ attachedReport + ", buildingId=" + buildingId + "]";
	}
	
}
