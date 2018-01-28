package com.timsedam.buildingmanagement.dto.response;

import java.util.List;

public class ReportDTO {

	private Long id;
	private String status;
	private String description;
	private Long location;
	private List<String> pictures;
	private Long senderId;
	private Long currentHolder;
	private List<CommentDTO> comments;
	
	public ReportDTO(){}
	
	public ReportDTO(Long id, String status, String description, Long location, 
			List<String> pictures, Long senderId, Long currentHolder, 
			List<CommentDTO> comments) {
		super();
		this.id = id;
		this.status = status;
		this.description = description;
		this.location = location;
		this.pictures = pictures;
		this.senderId = senderId;
		this.currentHolder = currentHolder;
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

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
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

	public Long getCurrentHolder() {
		return currentHolder;
	}

	public void setCurrentHolder(Long currentHolder) {
		this.currentHolder = currentHolder;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}
	
	
}
