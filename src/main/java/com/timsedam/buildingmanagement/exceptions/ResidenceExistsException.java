package com.timsedam.buildingmanagement.exceptions;

public class ResidenceExistsException extends Exception {

	private Long buildingId;
	private int apartmentNumber;
	private static final long serialVersionUID = 7250267963439748482L;
	
	public ResidenceExistsException(Long buildingId, int apartmentNumber) {
		super();
		this.buildingId = buildingId;
		this.apartmentNumber = apartmentNumber;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public int getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

}
