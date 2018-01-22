package com.timsedam.buildingmanagement.exceptions;

public class UserNotReportHolderException extends Exception {
	
	private Long userId;
	private Long reportId;
	
	public UserNotReportHolderException(Long userId, Long reportId) {
		super();
		this.userId = userId;
		this.reportId = reportId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
}
