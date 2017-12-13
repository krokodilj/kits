package com.timsedam.buildingmanagement.dto;

import java.util.List;

public class CreateReportDTO {
	
	private String description;
	private long building;
	private List<String> photos;
	
	public CreateReportDTO(){}

	public CreateReportDTO(String description, long building, List<String> photos) {
		super();
		this.description = description;
		this.building = building;
		this.photos = photos;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getBuilding() {
		return building;
	}

	public void setBuilding(long building) {
		this.building = building;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}
	
	
	

}
