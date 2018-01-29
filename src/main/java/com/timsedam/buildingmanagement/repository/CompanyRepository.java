package com.timsedam.buildingmanagement.repository;

import com.timsedam.buildingmanagement.model.Company;
import com.timsedam.buildingmanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company,Long> {
     List<Company> findAllByRoles(List<Role> roles);
}
