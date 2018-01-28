package com.timsedam.buildingmanagement.model;

import java.util.List;

import javax.persistence.*;

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

	@Lob
	@ElementCollection
	private List<String> pictures;
	
	@ManyToOne
	private User manager;
	
	@OneToMany(mappedBy = "building")
	private List<Residence> residences;
	
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
		return "Building [id=" + id + ", city=" + city + ", address=" + address + ", country=" + country
				+ ", apartmentCount=" + apartmentCount + ", description=" + description + ", pictures=" + pictures
				+ ", manager=" + manager + ", residences=" + residences + "]";
	}

}
