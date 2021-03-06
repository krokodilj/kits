package com.timsedam.buildingmanagement.exceptions;

public class BuildingExistsException extends Exception {
	
	private String address;
	private String city;
	private String country;
	private static final long serialVersionUID = 4101207182271731800L;
	
	public BuildingExistsException(String address, String city, String country) {
		super();
		this.address = address;
		this.city = city;
		this.country = country;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

}
