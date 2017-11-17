package com.timsedam.buildingmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Building {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String city;
	private String address;
	private String country;
	private int apartmentCount;
	private String description;
	private String picture;
	@ManyToOne
	private Manager manager;
	
	public Building() {}

	public Building(String city, String address, String country, int apartmentCount, String description, String picture,
			Manager manager) {
		this.city = city;
		this.address = address;
		this.country = country;
		this.apartmentCount = apartmentCount;
		this.description = description;
		this.picture = picture;
		this.manager = manager;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getApartmentCount() {
		return apartmentCount;
	}

	public void setApartmentCount(int apartmentCount) {
		this.apartmentCount = apartmentCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@Override
	public String toString() {
		return "Building [id=" + id + ", city=" + city + ", address=" + address + ", country=" + country
				+ ", apartmentCount=" + apartmentCount + ", description=" + description + ", picture=" + picture
				+ ", manager=" + manager + "]";
	}

}
