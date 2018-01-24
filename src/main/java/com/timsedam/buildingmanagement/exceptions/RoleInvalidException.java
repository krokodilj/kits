package com.timsedam.buildingmanagement.exceptions;

public class RoleInvalidException extends Exception {
	
	private String roleName;
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
