package com.timsedam.buildingmanagement.exceptions;

public class UserNotResidentException extends Exception {
	
	private Long userId;
	private Long buildingId;
	
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
