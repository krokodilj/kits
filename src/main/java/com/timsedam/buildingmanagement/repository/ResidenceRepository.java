package com.timsedam.buildingmanagement.repository;


import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResidenceRepository extends JpaRepository<Residence,Long>{

    public List<Residence> findAllByBuilding(Building b);

}
