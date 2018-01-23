package com.timsedam.buildingmanagement.exceptions;

public class UserExistsException extends Exception {

	private String username;
	private static final long serialVersionUID = -4991221691982459126L;
	
	public UserExistsException(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
