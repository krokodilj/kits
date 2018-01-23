package com.timsedam.buildingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.BuildingExistsException;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.BuildingRepository;

@Service
public class BuildingService {

    @Autowired
    BuildingRepository buildingRepository;
    
    public boolean exists(String address, String city, String country) {
    	if(buildingRepository.existsByAddressAndCityAndCountry(
    			address.toLowerCase().trim(),
    			city.toLowerCase().trim(),
    			country.toLowerCase().trim()))
    		return true;
    	else
    		return false;
    }
    
    public Building save(Building building) {
    	return buildingRepository.save(building);
    }
    
    public Building create(Building building) throws BuildingExistsException {
    	if(exists(building.getAddress(), building.getCity(), building.getCountry()))
    		throw new BuildingExistsException(building.getAddress(), building.getCity(), building.getCountry());
    	else
    		return buildingRepository.save(building);
    }

    public boolean isManager(Building building, User user) {
    	if(building.getManager().getId() == user.getId()) 
    		return true;
    	else
    		return false;
    }
    
    public boolean isResidentOrApartmentOwner(User user, Building building) {
    	for(Residence residence : building.getResidences()) {
    		if(residence.getApartmentOwner().getId() == user.getId()) {
    			return true;
    		}
    		else {
    			for(User resident : residence.getResidents()) {
    				if(resident.getId() == user.getId())
    					return true;
    			}
    		}
    	}
    	return false;
    }

    public List<Building> findAll(){
    	return buildingRepository.findAll();
    }
    
    public Building findOne(long id) throws BuildingMissingException {
    	Building building = buildingRepository.findOne(id);
    	if(building == null)
    		throw new BuildingMissingException(id);
    	else
    		return building;
    }
   
}
