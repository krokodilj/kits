package com.timsedam.buildingmanagement.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.timsedam.buildingmanagement.model.Role;

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
    private List<Role> roles;
    
	public UserRegisterDTO() {
		super();
	}
	
	public UserRegisterDTO(String username, String password, String email, String picture, List<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.picture = picture;
		this.roles = roles;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "UserRegisterDTO [username=" + username + ", password=" + password + ", email=" + email + ", picture="
				+ picture + ", roles=" + roles + "]";
	}

}
