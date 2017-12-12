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

import com.timsedam.buildingmanagement.dto.AdminDTO;
import com.timsedam.buildingmanagement.dto.AdminRegisterDTO;
import com.timsedam.buildingmanagement.model.Admin;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.service.RoleService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/admins/")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModelMapper modelMapper;
		
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<AdminDTO> register(@Valid @RequestBody AdminRegisterDTO adminRegisterDTO,
			BindingResult validationResult) throws ClassNotFoundException {
		
		if (validationResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(userService.exists(adminRegisterDTO.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			Admin admin = (Admin) modelMapper.map(adminRegisterDTO, Admin.class);
 			
 			Role role = roleService.findOneByName("ADMIN");
 			admin.setRole(role);
			userService.save(admin);
			
			AdminDTO responseData = modelMapper.map(admin, AdminDTO.class);
			return new ResponseEntity<AdminDTO>(responseData, HttpStatus.CREATED);
		}
		
	}

}
