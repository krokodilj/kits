package com.timsedam.buildingmanagement.model;

import java.util.Collection;

import javax.persistence.Entity;

@Entity
public class Resident extends User {
	
	private Building residence;
	private int floorNumber;
	private int apartmentNumber;
    
	public Resident() {
		super();
	}

	public Resident(String username, String password, String email, String picture, Role role,
			Collection<Comment> comments, Building residence, int floorNumber, int apartmentNumber) {
		super(username, password, email, picture, role, comments);
		this.residence = residence;
		this.floorNumber = floorNumber;
		this.apartmentNumber = apartmentNumber;
	}

	public Building getResidence() {
		return residence;
	}

	public void setResidence(Building residence) {
		this.residence = residence;
	}

	public int getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public int getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	@Override
	public String toString() {
		return "Resident [residence=" + residence + ", floorNumber=" + floorNumber + ", apartmentNumber="
				+ apartmentNumber + "]";
	}
    
}
