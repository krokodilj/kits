package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public boolean existsByUsername(String username);
	
	public User findByUsername(String username);

	public User findOneByUsernameAndPassword(String username,String password);

}
