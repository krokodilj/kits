package com.timsedam.buildingmanagement.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("company")
public class Company extends User {
	
	String name;
	String locaton;
	String PIB;
	String phoneNumber;
	
	public Company() {
		super();
	}

	public Company(String name, String locaton, String pIB, String phoneNumber) {
		super();
		this.name = name;
		this.locaton = locaton;
		PIB = pIB;
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocaton() {
		return locaton;
	}

	public void setLocaton(String locaton) {
		this.locaton = locaton;
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
		return "Company [name=" + name + ", locaton=" + locaton + ", PIB=" + PIB + ", phoneNumber=" + phoneNumber + "]";
	}

}
