package com.timsedam.buildingmanagement.exceptions;

public class UserNotResidentException extends Exception {
	
	private Long userId;
	private Long buildingId;
	private static final long serialVersionUID = 8529176239346608854L;
	
	public UserNotResidentException(Long userId, Long buildingId) {
		super();
		this.userId = userId;
		this.buildingId = buildingId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getBuildingId() {
		return buildingId;
	}
	
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

}
