package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import com.timsedam.buildingmanagement.dto.response.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.timsedam.buildingmanagement.dto.request.CompanyCreateDTO;
import com.timsedam.buildingmanagement.exceptions.RoleInvalidException;
import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.mapper.CompanyMapper;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.service.CompanyService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/api/companies/")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
		
	@Autowired
	private CompanyMapper companyMapper;

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> create(@Valid @RequestBody CompanyCreateDTO companyDTO, BindingResult validationResult)
			throws ClassNotFoundException, UserExistsException, RoleInvalidException {
		
		if (validationResult.hasErrors()) {
			String errorMessage = validationResult.getFieldError().getDefaultMessage();
			return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		else {
			Company company = companyMapper.toModel(companyDTO);
			Company savedCompany = companyService.create(company);
			
			return new ResponseEntity<Long>(savedCompany.getId(), HttpStatus.CREATED);
		}
		
	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<CompanyDTO>> getAll(){

		List<Company> companies = companyService.getAll();
		List<CompanyDTO> userDTOS = new ArrayList<CompanyDTO>();
		for(Company u : companies)
			userDTOS.add(companyMapper.toDto(u));

		return new ResponseEntity<List<CompanyDTO>>(userDTOS,HttpStatus.OK);
	}

	
	/**
	 * Handles UserExistsException that can happen when calling CompanyService.save(company)
	 */
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<String> userExistsException(final UserExistsException e) {
		return new ResponseEntity<String>("Company with username: " + e.getUsername() + " already exists.", HttpStatus.CONFLICT);
	}

}
