package com.timsedam.buildingmanagement.repository;

import com.timsedam.buildingmanagement.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sirko on 12/7/17.
 */
public interface BuildingRepository extends JpaRepository<Building, Long> {
}
