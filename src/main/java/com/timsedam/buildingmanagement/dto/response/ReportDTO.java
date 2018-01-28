package com.timsedam.buildingmanagement.dto.response;

import java.util.List;

public class ReportDTO {

	private Long id;
	private String status;
	private String description;
	private BuildingDTO location;
	private List<String> pictures;
	private Long senderId;
	private ForwardDTO currentHolder;
	private List<CommentDTO> comments;
	
	public ReportDTO(){}
	
	public ReportDTO(Long id, String status, String description, 
			List<String> pictures, Long senderId, 
			List<CommentDTO> comments) {
		super();
		this.id = id;
		this.status = status;
		this.description = description;
		this.pictures = pictures;
		this.senderId = senderId;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BuildingDTO getLocation() {
		return location;
	}

	public void setLocation(BuildingDTO location) {
		this.location = location;
	}

	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public ForwardDTO getCurrentHolder() {
		return currentHolder;
	}

	public void setCurrentHolder(ForwardDTO currentHolder) {
		this.currentHolder = currentHolder;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	
	
}
