package com.timsedam.buildingmanagement.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.timsedam.buildingmanagement.dto.request.CreateResidenceDTO;
import com.timsedam.buildingmanagement.dto.response.ResidenceDTO;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.User;

@Component
public class ResidenceMapper {

    public Residence toModel(CreateResidenceDTO r){
        Residence residence = 
        	new Residence(null, r.getFloorNumber(), r.getApartmentNumber(), new ArrayList<User>());
        return residence;
    }

    public ResidenceDTO toDto(Residence r){
        ResidenceDTO residenceDTO = new ResidenceDTO(r.getId(), r.getBuilding().getId(), r.getFloorNumber(),
        		r.getApartmentNumber(), null, r.getApartmentOwner().getId());

        //residents
        if(r.getResidents() != null){
        	ArrayList<Long> residents = new ArrayList<Long>();
            for(User resident : r.getResidents())
            	residents.add(resident.getId());
            residenceDTO.setResidents(residents);
        }
        
        return residenceDTO;
    }

}