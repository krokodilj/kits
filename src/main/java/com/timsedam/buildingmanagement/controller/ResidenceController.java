package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.request.ResidenceCreateDTO;
import com.timsedam.buildingmanagement.dto.response.ResidenceDTO;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.exceptions.ResidenceExistsException;
import com.timsedam.buildingmanagement.exceptions.ResidenceMissingException;
import com.timsedam.buildingmanagement.mapper.ResidenceMapper;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.ResidenceService;

@RestController
@RequestMapping(value = "/api/residences/")
public class ResidenceController {

    @Autowired
    private ResidenceService residenceService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ResidenceMapper residenceMapper;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody ResidenceCreateDTO residenceCreateDTO,
    		BindingResult validationResult) throws BuildingMissingException, ResidenceExistsException {
        if (validationResult.hasErrors()) {
        	String errorMessage = validationResult.getFieldError().getDefaultMessage();
        	return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Building building = buildingService.findOne(residenceCreateDTO.getBuilding());
        Residence residence = residenceMapper.toModel(residenceCreateDTO);
        residence.setBuilding(building);
        residence = residenceService.create(residence);

        return new ResponseEntity<Long>(residence.getId(), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> get(@PathVariable long id) throws ResidenceMissingException{
        Residence residence = residenceService.findOneById(id);
        ResidenceDTO residenceDTO = residenceMapper.toDto(residence);
        return new ResponseEntity<ResidenceDTO>(residenceDTO, HttpStatus.OK);
    }
    
	/**
	 * Handles BuildingMissingException that can happen when calling buildingService.findOne(buildingId)
	 */
	@ExceptionHandler(BuildingMissingException.class)
	public ResponseEntity<String> buildingMissingException(final BuildingMissingException e) {
		return new ResponseEntity<String>("Building with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
    
	/**
	 * Handles ResidenceExistsException that can happen when calling residenceService.save(residence)
	 */
	@ExceptionHandler(ResidenceExistsException.class)
	public ResponseEntity<String> residenceExistsException(final ResidenceExistsException e) {
		return new ResponseEntity<String>("Residence with apartmentNumber: " + e.getApartmentNumber() + 
				" already exists in Building with id: " + e.getBuildingId(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	/**
	 * Handles ResidenceMissingException that can happen when calling residenceService.findOne(residenceId)
	 */
	@ExceptionHandler(ResidenceMissingException.class)
	public ResponseEntity<String> ResidenceMissingException(final ResidenceMissingException e) {
		return new ResponseEntity<String>("Residence with id: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}


}