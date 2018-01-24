package com.timsedam.buildingmanagement.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Residence;

public interface ResidenceRepository extends JpaRepository<Residence, Long>{

}
