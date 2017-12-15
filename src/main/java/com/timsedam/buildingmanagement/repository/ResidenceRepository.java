package com.timsedam.buildingmanagement.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;

public interface ResidenceRepository extends JpaRepository<Residence,Long>{

    public List<Residence> findAllByBuilding(Building b);

}
