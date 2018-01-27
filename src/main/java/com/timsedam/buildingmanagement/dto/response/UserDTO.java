package com.timsedam.buildingmanagement.dto.response;

import java.util.List;

public class UserDTO {
	
	private Long id;
	private String username;
	private String email;
	private List<String> pictures;   
	private List<String> roles; 
    
	public UserDTO(){}
	
	public UserDTO(Long id, String username, String email, List<String> pictures, List<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.pictures = pictures;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	

}
