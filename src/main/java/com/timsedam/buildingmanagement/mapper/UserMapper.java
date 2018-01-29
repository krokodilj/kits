package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.UserRegisterDTO;
import com.timsedam.buildingmanagement.dto.response.UserDTO;
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
		user.setPictures(dto.getPictures());
		user.setComments(new ArrayList<Comment>());
		user.setRoles(new ArrayList<Role>());
		
		return user;
	}
	
	public UserDTO toDto(User u){
        UserDTO dto = new UserDTO(u.getId(), u.getUsername(), u.getEmail(), u.getPictures(), null);
        List<String> roles = new ArrayList<String>();
        for(Role r : u.getRoles())
    		roles.add(r.getName());
    	dto.setRoles(roles);
        return dto;
    }

}
