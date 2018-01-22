package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.model.User;

@Component
public class UserMapper {
	
	public User toModel(UserRegisterDTO dto) {
		User user = new User();
		user.setUsername(dto.getUsername());
		user.setPassword(dto.getPassword());
		user.setEmail(dto.getEmail());
		user.setPictures(new ArrayList<String>());
		user.setComments(new ArrayList<Comment>());
		user.setRoles(new ArrayList<Role>());
		
		return user;
	}

}
