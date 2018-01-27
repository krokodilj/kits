package com.timsedam.buildingmanagement.repository;

import com.timsedam.buildingmanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public boolean existsByUsername(String username);
	
	public User findByUsername(String username);

	public User findOneByUsernameAndPassword(String username,String password);

	public List<User> findAllByRoles(List<Role> roles);
}
