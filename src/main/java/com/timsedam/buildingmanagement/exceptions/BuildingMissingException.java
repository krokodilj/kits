package com.timsedam.buildingmanagement.exceptions;

public class BuildingMissingException extends Exception {
	
	private Long id;

	public BuildingMissingException(Long id) {
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
