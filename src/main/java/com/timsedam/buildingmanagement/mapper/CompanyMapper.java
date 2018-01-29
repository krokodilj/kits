package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;

import com.timsedam.buildingmanagement.dto.response.CompanyDTO;
import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.CompanyCreateDTO;
import com.timsedam.buildingmanagement.model.Comment;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.Role;

@Component
public class CompanyMapper {
	
	public Company toModel(CompanyCreateDTO dto) {
		Company company = new Company();
		company.setUsername(dto.getUsername());
		company.setPassword(dto.getPassword());
		company.setEmail(dto.getEmail());
		company.setPictures(new ArrayList<String>());
		company.setComments(new ArrayList<Comment>());
		company.setRoles(new ArrayList<Role>());
		company.setName(dto.getName());
		company.setLocation(dto.getLocation());
		company.setPIB(dto.getPIB());
		company.setPhoneNumber(dto.getPhoneNumber());
		
		return company;
	}

	public CompanyDTO toDto(Company company){
		CompanyDTO dto = new CompanyDTO();
		dto.setId(company.getId());
		dto.setUsername(company.getUsername());
		dto.setEmail(company.getEmail());
		dto.setPictures(company.getPictures());
		dto.setName(company.getName());
		dto.setEmail(company.getEmail());
		dto.setPIB(company.getPIB());
		dto.setPhoneNumber(company.getPhoneNumber());
		return dto;
	}

}
