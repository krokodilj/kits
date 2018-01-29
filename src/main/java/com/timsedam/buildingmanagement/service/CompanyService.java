package com.timsedam.buildingmanagement.service;

import com.timsedam.buildingmanagement.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.repository.RoleRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CompanyRepository companyRepository;
	
	private boolean exists(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public Company create(Company company) throws UserExistsException {
		if(exists(company.getUsername()))
			throw new UserExistsException(company.getUsername());
		
		Role role = roleRepository.findOneByName("COMPANY");
		company.getRoles().add(role);
		return userRepository.save(company);
	}

	public List<Company> getAll(){
		ArrayList<Role> r = new ArrayList<Role>();
		Role ro=new Role();
		ro.setId((long) 2);
		r.add(ro);
		return companyRepository.findAllByRoles(r);
	}

}
