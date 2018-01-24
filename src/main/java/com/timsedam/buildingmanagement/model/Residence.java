package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Residence {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private int floorNumber;
	
	private int apartmentNumber;
	
	@ManyToOne
	private Building building;
	
	@ManyToOne
	private User apartmentOwner;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "resident_residence", 
		joinColumns = @JoinColumn(name="residence_id"), 
		inverseJoinColumns = @JoinColumn(name="resident_id")) 
	private List<User> residents;
	
	
	public Residence() {
		super();
	}

	public Residence(Building building, int floorNumber, int apartmentNumber, List<User> residents) {
		super();
		this.building = building;
		this.floorNumber = floorNumber;
		this.apartmentNumber = apartmentNumber;
		this.residents = residents;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
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

	public List<User> getResidents() {
		return residents;
	}

	public void setResidents(List<User> residents) {
		this.residents = residents;
	}

	public User getApartmentOwner() {
		return apartmentOwner;
	}

	public void setApartmentOwner(User apartmentOwner) {
		this.apartmentOwner = apartmentOwner;
	}

	@Override
	public String toString() {
		return "Residence [id=" + id + ", floorNumber=" + floorNumber + ", apartmentNumber="
				+ apartmentNumber + ", Residents=" + residents + "]";
	}

}
