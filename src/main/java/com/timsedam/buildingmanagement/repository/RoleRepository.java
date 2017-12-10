package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Role findOneByName(String name);

}
