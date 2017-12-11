package com.timsedam.buildingmanagement.service;

import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sirko on 12/7/17.
 */
@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;

    public Building createBuilding(Building building){
        try{
            return buildingRepository.save(building);
        }catch(Exception e){
            return null;
        }

    }

}
