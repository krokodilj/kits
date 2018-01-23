package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.CompanyRegisterDTO;
import com.timsedam.buildingmanagement.exceptions.RoleInvalidException;
import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.mapper.CompanyMapper;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.service.CompanyService;

@RestController
@RequestMapping(value="/api/companies/")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
		
	@Autowired
	private CompanyMapper companyMapper;
		
	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> register(@Valid @RequestBody CompanyRegisterDTO companyRegisterDTO,
			BindingResult validationResult) throws ClassNotFoundException, UserExistsException, RoleInvalidException {
		
		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			System.out.println(validationResult.getFieldErrors());
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			Company company = companyMapper.toModel(companyRegisterDTO);
			Company savedCompany = companyService.save(company);
			
			return new ResponseEntity<Long>(savedCompany.getId(), HttpStatus.CREATED);
		}
		
	}
	
	/**
	 * Handles UserExistsException that can happen when calling CompanyService.save(company)
	 */
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<String> userExistsException(final UserExistsException e) {
		return new ResponseEntity<String>("Company with username: " + e.getUsername() + " already exists.", HttpStatus.NOT_FOUND);
	}

}
