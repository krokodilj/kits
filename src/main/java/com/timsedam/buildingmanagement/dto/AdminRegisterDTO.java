package com.timsedam.buildingmanagement.dto;

import java.util.ArrayList;

public class AdminRegisterDTO extends UserRegisterDTO {

	public AdminRegisterDTO() {
		super();
	}

	public AdminRegisterDTO(String username, String password, String email, ArrayList<String> pictures) {
		super(username, password, email, pictures);
	}
	
	public AdminRegisterDTO(AdminRegisterDTO adminRegisterDTO) throws CloneNotSupportedException {
		super(adminRegisterDTO);
	}

}
