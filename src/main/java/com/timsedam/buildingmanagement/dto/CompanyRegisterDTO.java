package com.timsedam.buildingmanagement.dto;

import javax.validation.constraints.NotNull;

public class CompanyRegisterDTO extends UserRegisterDTO {
	
	@NotNull
	private String name;
	private String locaton;
	@NotNull
	private String PIB;
	@NotNull
	private String phoneNumber;

	public CompanyRegisterDTO() {
		super();
	}

	public CompanyRegisterDTO(String username, String password, String email, String picture, String name,
			String locaton, String pIB, String phoneNumber) {
		super(username, password, email, picture);
		this.name = name;
		this.locaton = locaton;
		PIB = pIB;
		this.phoneNumber = phoneNumber;
	}

	public CompanyRegisterDTO(CompanyRegisterDTO companyRegisterDTO) {
		super(companyRegisterDTO);
		this.name = new String(companyRegisterDTO.getName());
		this.locaton = new String(companyRegisterDTO.getLocaton());
		this.PIB = new String(companyRegisterDTO.getPIB());
		this.phoneNumber = new String(companyRegisterDTO.getPhoneNumber());
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
		return "CompanyRegisterDTO [name=" + name + ", locaton=" + locaton + ", PIB=" + PIB + ", phoneNumber="
				+ phoneNumber + "]";
	}

}
