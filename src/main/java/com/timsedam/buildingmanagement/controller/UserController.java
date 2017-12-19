package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.UserDTO;
import com.timsedam.buildingmanagement.dto.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.RoleService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/users/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO,
			BindingResult validationResult) throws ClassNotFoundException {
				
		if (validationResult.hasErrors()) {
			String errorMessage = "Validation failed!\n"
					+ "username must be atleast 4 characters long,\n"
					+ "password must be atleast 6 characters long,\n"
					+ "email must be of the valid format.";
			return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(userService.exists(userRegisterDTO.getUsername())) {
			String errorMessage = "User with the provided username already exists.";
			return new ResponseEntity<String>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		else {
 			User user = (User) modelMapper.map(userRegisterDTO, User.class);
 			
 			Role role = roleService.findOneByName("USER");
 			user.setRole(role);
			userService.save(user);
			
			UserDTO responseData = modelMapper.map(user, UserDTO.class);
			
			return new ResponseEntity<UserDTO>(responseData, HttpStatus.CREATED);
		}
		
	}

}
