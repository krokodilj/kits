package com.timsedam.buildingmanagement.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Role;

public class UserDTO {
	
	private Long id;
	private String username;
	private String email;
	private ArrayList<String> pictures;   
    private Role role;
    private List<Comment> comments;
    
	public UserDTO() {
		super();
	}

	public UserDTO(Long id, String username, String email, ArrayList<String> pictures, Role role,
			List<Comment> comments) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.pictures = pictures;
		this.role = role;
		this.comments = comments;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ArrayList<String> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<String> pictures) {
		this.pictures = pictures;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", email=" + email + ", pictures=" + pictures
				+ ", role=" + role + ", comments=" + comments + "]";
	}

}
