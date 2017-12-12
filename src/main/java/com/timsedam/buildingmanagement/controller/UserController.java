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
	public ResponseEntity<UserDTO> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO,
			BindingResult validationResult) throws ClassNotFoundException {
		
		if (validationResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(userService.exists(userRegisterDTO.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
