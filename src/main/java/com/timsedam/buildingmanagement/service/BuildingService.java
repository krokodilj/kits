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

    public boolean createBuilding(Building building){
        try{
            buildingRepository.save(building);
            return true;
        }catch(Exception e){
            return false;
        }

    }

}
