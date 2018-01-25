package com.timsedam.buildingmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.model.Role;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.RoleRepository;
import com.timsedam.buildingmanagement.repository.UserRepository;

@Service
public class AdminService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private boolean exists(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public User create(User user) throws UserExistsException {
		if(exists(user.getUsername()))
			throw new UserExistsException(user.getUsername());
		
		Role role = roleRepository.findOneByName("ADMIN");
		user.getRoles().add(role);
		return userRepository.save(user);
	}

}
