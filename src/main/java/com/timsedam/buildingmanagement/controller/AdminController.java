package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import com.timsedam.buildingmanagement.dto.response.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.timsedam.buildingmanagement.dto.request.UserRegisterDTO;
import com.timsedam.buildingmanagement.exceptions.RoleInvalidException;
import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.mapper.UserMapper;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.AdminService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admins/")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private UserMapper userMapper;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO adminRegisterDTO,
			BindingResult validationResult) throws ClassNotFoundException, UserExistsException, RoleInvalidException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		else {
			User admin = userMapper.toModel(adminRegisterDTO);
			User savedAdmin = adminService.create(admin);
			
			return new ResponseEntity<Long>(savedAdmin.getId(), HttpStatus.CREATED);
		}

	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<UserDTO>> getAll(){

		List<User> admins = adminService.getAll();
		List<UserDTO> userDTOS = new ArrayList<UserDTO>();
		for(User u : admins)
			userDTOS.add(userMapper.toDto(u));

		return new ResponseEntity<List<UserDTO>>(userDTOS,HttpStatus.OK);
	}

	/**
	 * Handles UserExistsException that can happen when calling AdminService.save(user)
	 */
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<String> userExistsException(final UserExistsException e) {
		return new ResponseEntity<String>("Admin with username: " + e.getUsername() + " already exists.", HttpStatus.CONFLICT);
	}

}
