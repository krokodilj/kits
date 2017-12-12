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

import com.timsedam.buildingmanagement.dto.CompanyDTO;
import com.timsedam.buildingmanagement.dto.CompanyRegisterDTO;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.service.RoleService;
import com.timsedam.buildingmanagement.service.UserService;

@RestController
@RequestMapping(value="/api/companies/")
public class CompanyController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ModelMapper modelMapper;
		
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<CompanyDTO> register(@Valid @RequestBody CompanyRegisterDTO companyRegisterDTO,
			BindingResult validationResult) throws ClassNotFoundException {
		
		if (validationResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else if(userService.exists(companyRegisterDTO.getUsername())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			Company company = (Company) modelMapper.map(companyRegisterDTO, Company.class);
 			
 			Role role = roleService.findOneByName("COMPANY");
 			company.setRole(role);
			userService.save(company);
			
			CompanyDTO responseData = modelMapper.map(company, CompanyDTO.class);
			return new ResponseEntity<CompanyDTO>(responseData, HttpStatus.CREATED);
		}
		
	}

}
