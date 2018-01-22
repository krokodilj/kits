package com.timsedam.buildingmanagement.exceptions;

public class RoleInvalidException extends Exception {
	
	private String roleName;
	
	private static final long serialVersionUID = 7982269622873488691L;

	public RoleInvalidException(String roleName) {
		super();
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
