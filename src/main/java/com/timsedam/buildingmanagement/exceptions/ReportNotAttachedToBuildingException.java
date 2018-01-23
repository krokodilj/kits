package com.timsedam.buildingmanagement.exceptions;

public class ReportNotAttachedToBuildingException extends Exception {
	
	private Long reportId;
	private Long buildingId;
	
	public ReportNotAttachedToBuildingException(Long reportId, Long buildingId) {
		super();
		this.reportId = reportId;
		this.buildingId = buildingId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	
}
