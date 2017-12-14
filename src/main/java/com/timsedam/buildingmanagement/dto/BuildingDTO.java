package com.timsedam.buildingmanagement.dto;


import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.User;

import java.util.List;

public class BuildingDTO {

    private long id;
    private String city;
    private String address;
    private String country;
    private int apartmentCount;
    private String description;
    private List<String> pictures;
    private User manager;
    private List<Residence> residences;
    
	public BuildingDTO() {
		super();
	}

	public BuildingDTO(long id, String city, String address, String country, int apartmentCount, String description,
			List<String> pictures, User manager, List<Residence> residences) {
		super();
		this.id = id;
		this.city = city;
		this.address = address;
		this.country = country;
		this.apartmentCount = apartmentCount;
		this.description = description;
		this.pictures = pictures;
		this.manager = manager;
		this.residences = residences;
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

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<Residence> getResidences() {
		return residences;
	}

	public void setResidences(List<Residence> residences) {
		this.residences = residences;
	}

	@Override
	public String toString() {
		return "BuildingDTO [id=" + id + ", city=" + city + ", address=" + address + ", country=" + country
				+ ", apartmentCount=" + apartmentCount + ", description=" + description + ", pictures=" + pictures
				+ ", manager=" + manager + ", residences=" + residences + "]";
	}
    
}

