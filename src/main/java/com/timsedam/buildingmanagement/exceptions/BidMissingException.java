package com.timsedam.buildingmanagement.exceptions;

public class BidMissingException extends Exception {
	
	private Long id;

	public BidMissingException(Long id) {
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
