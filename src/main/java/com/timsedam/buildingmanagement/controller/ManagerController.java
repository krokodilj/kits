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
import com.timsedam.buildingmanagement.service.ManagerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/managers/")
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	@Autowired
	private UserMapper userMapper;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO managerRegisterDTO,
			BindingResult validationResult) throws ClassNotFoundException, UserExistsException, RoleInvalidException {

		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		} 
		else {
			User manager = userMapper.toModel(managerRegisterDTO);
			User savedManager = managerService.save(manager);
			
			return new ResponseEntity<Long>(savedManager.getId(), HttpStatus.CREATED);
		}

	}

	@GetMapping(produces="application/json")
	public ResponseEntity<List<UserDTO>> get(){
		List<User> managers = managerService.getAll();
		List<UserDTO> managerDTOs = new ArrayList<UserDTO>();
		for (User u: managers ) {
			managerDTOs.add(userMapper.toDto(u));
		}
		return new ResponseEntity<List<UserDTO>>(managerDTOs,HttpStatus.OK);
	}

	/**
	 * Handles UserExistsException that can happen when calling AdminService.save(user)
	 */
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<String> userExistsException(final UserExistsException e) {
		return new ResponseEntity<String>("Manager with username: " + e.getUsername() + " already exists.", HttpStatus.CONFLICT);
	}

}
