package com.timsedam.buildingmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Building;

/**
 * Created by sirko on 12/7/17.
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {
	
	public boolean existsByAddressAndCityAndCountry(String address, String city, String country);
	
}
