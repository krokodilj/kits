package com.timsedam.buildingmanagement.dto.response;

import java.time.LocalDateTime;

public class CommentDTO {

	private long id;
	private String data;
	private UserDTO commenter;
	private LocalDateTime postedAt;
	
	public CommentDTO(){}
	
	public CommentDTO(long id, String data, UserDTO commenter, LocalDateTime postedAt) {
		super();
		this.id = id;
		this.data = data;
		this.commenter = commenter;
		this.postedAt = postedAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public UserDTO getCommenter() {
		return commenter;
	}

	public void setCommenter(UserDTO commenter) {
		this.commenter = commenter;
	}

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}
	

}
