package com.timsedam.buildingmanagement.exceptions;

public class ResidenceMissingException extends Exception {
	
	private Long id;

	private static final long serialVersionUID = 1294407092223808017L;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
