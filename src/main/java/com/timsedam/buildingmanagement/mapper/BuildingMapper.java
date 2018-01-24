package com.timsedam.buildingmanagement.mapper;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.BuildingCreateDTO;
import com.timsedam.buildingmanagement.dto.response.BuildingDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;

@Component
public class BuildingMapper {

    public Building toModel(BuildingCreateDTO cb){
    	Building building = new Building();
    	
    	building.setAddress(cb.getAddress());
    	building.setApartmentCount(cb.getApartmentCount());
    	building.setCity(cb.getCity());
    	building.setCountry(cb.getCountry());
    	building.setDescription(cb.getDescription());
    	
    	return building;
    }
    
    public BuildingDTO toDto(Building b){
        BuildingDTO dto = 
        	new BuildingDTO(b.getId(), b.getCity(), b.getAddress(), b.getCountry(), 
        		b.getApartmentCount(), b.getDescription(), null, null, null);

        if(b.getPictures() != null) {
        	ArrayList<String> pictures = new ArrayList<String>();
        	for(String picture : b.getPictures())
        		pictures.add(picture);
        	dto.setPictures(pictures);
        }
        
        if(b.getManager() != null)
        	dto.setManager(b.getManager().getId());
        	
        if(b.getResidences() != null) {
        	ArrayList<Long> residences = new ArrayList<Long>();
        	for(Residence r : b.getResidences())
        		residences.add(r.getId());
        	dto.setResidences(residences);
        }

        return dto;
    }
    
    public ArrayList<BuildingDTO> toDto(List<Building> buildings) {
    	ArrayList<BuildingDTO> result = new ArrayList<BuildingDTO>();
    	for(Building building : buildings) {
    		result.add(toDto(building));
    	}
    	return result;
    }

}
