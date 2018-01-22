package com.timsedam.buildingmanagement.exceptions;

public class UserMissingException extends Exception {

	private Long id;
	private String username;
	
	public UserMissingException() {}

	public UserMissingException(Long id) {
		super();
		this.id = id;
	}
	
	public UserMissingException(String username) {
		super();
		this.username = username;
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
	
}
