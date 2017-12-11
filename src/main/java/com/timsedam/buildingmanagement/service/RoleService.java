package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public Role findOneByName(String name) {
		String upperCasedName = name.toUpperCase();
		return roleRepository.findOneByName(upperCasedName);
	}

}
