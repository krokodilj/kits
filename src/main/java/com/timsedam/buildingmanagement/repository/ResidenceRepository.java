package com.timsedam.buildingmanagement.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.timsedam.buildingmanagement.model.Residence;

public interface ResidenceRepository extends JpaRepository<Residence, Long>{

	List<Residence> findAllByApartmentOwnerId(long apartmentOwnerId);
	
	@Query("SELECT r FROM Residence as r join r.residents as rr where ?1=rr.id")
	List<Residence> findAllByResidentId(Long residentId);

	List<Residence> findAllByBuildingId(long buildingId);
}
