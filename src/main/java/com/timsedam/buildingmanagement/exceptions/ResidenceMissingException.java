package com.timsedam.buildingmanagement.exceptions;

public class ResidenceMissingException extends Exception {
	
	private Long id;

	public ResidenceMissingException(Long id) {
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
