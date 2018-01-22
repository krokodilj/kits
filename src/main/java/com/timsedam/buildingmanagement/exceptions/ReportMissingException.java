package com.timsedam.buildingmanagement.exceptions;

public class ReportMissingException extends Exception {
	
	private Long id;

	public ReportMissingException(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
