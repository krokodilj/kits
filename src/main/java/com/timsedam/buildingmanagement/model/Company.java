package com.timsedam.buildingmanagement.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("COMPANY")
public class Company extends User {
	
	private String name;
	private String location;
	private String PIB;
	private String phoneNumber;
	
	public Company() {
		super();
	}

	public Company(String name, String location, String pIB, String phoneNumber) {
		super();
		this.name = name;
		this.location = location;
		PIB = pIB;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPIB() {
		return PIB;
	}

	public void setPIB(String pIB) {
		PIB = pIB;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", location=" + location + ", PIB=" + PIB + ", phoneNumber=" + phoneNumber
				+ "]";
	}

}
