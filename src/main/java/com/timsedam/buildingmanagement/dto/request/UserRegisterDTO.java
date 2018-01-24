package com.timsedam.buildingmanagement.dto.request;

import java.util.ArrayList;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserRegisterDTO {
	
	@NotNull(message = "'username' not provided")
	@Size(min = 4, message = "'username' must be at least 4 characters long")
	private String username;
	
	@NotNull(message = "'password' not provided")
	@Size(min = 6, message = "'password' must be at least 6 characters long")
	private String password;
	
	@Email(message = "'email' must be of valid format")
	private String email;
	
	private ArrayList<String> pictures;
    
	public UserRegisterDTO() {
		super();
	}
	
	public UserRegisterDTO(String username, String password, String email, ArrayList<String> pictures) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.pictures = pictures;
	}

	@SuppressWarnings("unchecked")
	public UserRegisterDTO(UserRegisterDTO userRegDto) throws CloneNotSupportedException {
		this.username = new String(userRegDto.getUsername());
		this.password = new String(userRegDto.getPassword());
		this.email = new String(userRegDto.getEmail());
		this.pictures = (ArrayList<String>) userRegDto.getPictures().clone();
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

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	@Override
	public String toString() {
		return "UserRegisterDTO [username=" + username + ", password=" + password + ", email=" + email + ", pictures="
				+ pictures + "]";
	}
	
}
