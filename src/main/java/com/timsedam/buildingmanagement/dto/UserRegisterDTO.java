package com.timsedam.buildingmanagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserRegisterDTO {
	
	@NotNull
	@Size(min = 4)
	private String username;
	@NotNull
	@Size(min = 6)
	private String password;
	@Email
	private String email;
	private String picture;
    
	public UserRegisterDTO() {
		super();
	}

	public UserRegisterDTO(String username, String password, String email, String picture) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.picture = picture;
	}
	
	public UserRegisterDTO(UserRegisterDTO userRegDto) {
		this.username = new String(userRegDto.getUsername());
		this.password = new String(userRegDto.getPassword());
		this.email = new String(userRegDto.getEmail());
		this.picture = new String(userRegDto.getPicture());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		return "UserRegisterDTO [username=" + username + ", password=" + password + ", email=" + email + ", picture="
				+ picture + "]";
	}
	
}
