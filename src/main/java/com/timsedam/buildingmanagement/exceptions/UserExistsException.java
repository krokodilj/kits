package com.timsedam.buildingmanagement.exceptions;

public class UserExistsException extends Exception {

	private String username;
	
	public UserExistsException(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
