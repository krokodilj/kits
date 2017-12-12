package com.timsedam.buildingmanagement.dto;

public class AdminRegisterDTO extends UserRegisterDTO {

	public AdminRegisterDTO() {
		super();
	}

	public AdminRegisterDTO(String username, String password, String email, String picture) {
		super(username, password, email, picture);
	}
	
	public AdminRegisterDTO(AdminRegisterDTO adminRegisterDTO) {
		super(adminRegisterDTO);
	}

}
