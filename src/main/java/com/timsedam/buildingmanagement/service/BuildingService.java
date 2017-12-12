package com.timsedam.buildingmanagement.service;

import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Building findOneById(long id){
        try{
            return buildingRepository.findOne(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Building> getAll(){
        try{
            return buildingRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
