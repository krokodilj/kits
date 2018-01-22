package com.timsedam.buildingmanagement.dto.request;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;

public class CompanyRegisterDTO extends UserRegisterDTO {
	
	@NotNull(message = "{company.name.empty}")
	private String name;
	private String location;
	@NotNull(message = "{company.pib.empty}")
	private String PIB;
	@NotNull(message = "{company.phoneNumber.empty}")
	private String phoneNumber;

	public CompanyRegisterDTO() {
		super();
	}

	public CompanyRegisterDTO(String username, String password, String email, ArrayList<String> pictures, String name,
			String location, String pIB, String phoneNumber) {
		super(username, password, email, pictures);
		this.name = name;
		this.location = location;
		this.PIB = pIB;
		this.phoneNumber = phoneNumber;
	}

	public CompanyRegisterDTO(CompanyRegisterDTO companyRegisterDTO) throws CloneNotSupportedException {
		super(companyRegisterDTO);
		this.name = new String(companyRegisterDTO.getName());
		this.location = new String(companyRegisterDTO.getLocation());
		this.PIB = new String(companyRegisterDTO.getPIB());
		this.phoneNumber = new String(companyRegisterDTO.getPhoneNumber());
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
		return "CompanyRegisterDTO [name=" + name + ", location=" + location + ", PIB=" + PIB + ", phoneNumber="
				+ phoneNumber + "]";
	}

}
