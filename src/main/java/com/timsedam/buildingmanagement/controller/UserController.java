package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.timsedam.buildingmanagement.transformator.UserTypeStringToClass;
import com.timsedam.buildingmanagement.validator.UserTypeValidator;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserTypeValidator userTypeValidator;
	
	@Autowired
	private UserTypeStringToClass userTypeStringToClass;
	
	@PostMapping(value = "/{userType}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserDTO> register(@PathVariable String userType,
			@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult validationResult) throws ClassNotFoundException {
		
		if (validationResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if (!userTypeValidator.isValid(userType)) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(userService.exists(userRegisterDTO.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			Class<?> destinationClass = userTypeStringToClass.transform(userType);
 			User user = (User) modelMapper.map(userRegisterDTO, destinationClass);
 			
 			Role role = roleService.findOneByName(userType);
 			user.setRole(role);
			userService.save(user);
			
			UserDTO responseData = modelMapper.map(user, UserDTO.class);
			return new ResponseEntity<UserDTO>(responseData, HttpStatus.CREATED);
		}
		
	}

}
