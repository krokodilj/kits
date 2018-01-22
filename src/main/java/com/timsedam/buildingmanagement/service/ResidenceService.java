package com.timsedam.buildingmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timsedam.buildingmanagement.exceptions.ResidenceExistsException;
import com.timsedam.buildingmanagement.exceptions.ResidenceMissingException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.repository.ResidenceRepository;

@Service
public class ResidenceService {
	
	@Autowired
	private UserService userService;

    @Autowired
    private ResidenceRepository residenceRepository;
    
    //if there exists a Residence on the specified apartmentNumber
    public boolean exists(Building building, int apartmentNumber) { 
        if (building.getResidences() != null)
            for(Residence residence : building.getResidences())
                if(residence.getApartmentNumber() == apartmentNumber)
                    return true;
        return false;
    }

    public Residence save(Residence residence) {
    	return residenceRepository.save(residence);
    }
    
    public Residence create(Residence residence) throws ResidenceExistsException {
        	if(exists(residence.getBuilding(), residence.getApartmentNumber()))
        		throw new ResidenceExistsException(residence.getBuilding().getId(), residence.getApartmentNumber());
        	return residenceRepository.save(residence);
    }

    public Residence findOneById(long id) throws ResidenceMissingException{
    	Residence residence = residenceRepository.findOne(id);
    	if(residence == null)
    		throw new ResidenceMissingException(id);
    	else
    		return residence;
    }

    public List<Residence> getAllByBuilding(Building b){
        try{
            return residenceRepository.findAllByBuilding(b);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public void addResident(Long residenceId, Long userId) throws UserMissingException, ResidenceMissingException {
        User resident = userService.findOne(userId);
        Residence residence = findOneById(residenceId);

        residence.getResidents().add(resident);
        residenceRepository.save(residence);
    }

}