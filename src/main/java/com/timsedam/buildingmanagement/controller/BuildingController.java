package com.timsedam.buildingmanagement.controller;


import java.util.List;

import javax.validation.Valid;

import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.UserService;
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

import com.timsedam.buildingmanagement.dto.request.BuildingCreateDTO;
import com.timsedam.buildingmanagement.dto.response.BuildingDTO;
import com.timsedam.buildingmanagement.exceptions.BuildingExistsException;
import com.timsedam.buildingmanagement.exceptions.BuildingMissingException;
import com.timsedam.buildingmanagement.mapper.BuildingMapper;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.service.BuildingService;

@RestController
@RequestMapping(value = "/api/buildings/")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
	private UserService userService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> create(
            @Valid @RequestBody BuildingCreateDTO buildingCreateDTO, BindingResult validationResult)
			throws BuildingExistsException, UserMissingException {

    	if (validationResult.hasErrors()) {
        	String errorMessage = validationResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	else {
    		Building building = buildingMapper.toModel(buildingCreateDTO);
    		User m = userService.findOne((long)buildingCreateDTO.getManagerId());
    		building.setManager(m);
    		Building savedBuilding = buildingService.create(building);

    		return new ResponseEntity<Long>(savedBuilding.getId(), HttpStatus.CREATED);
    	}
    }
    
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> get() {
        List<Building> buildings = buildingService.findAll();
        List<BuildingDTO> buildingsDTO = buildingMapper.toDto(buildings);
        return new ResponseEntity<List<BuildingDTO>>(buildingsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{buildinId}", produces = "application/json")
    public ResponseEntity<?> get(@PathVariable long buildinId) throws BuildingMissingException {
        Building building = buildingService.findOne(buildinId);
        
        BuildingDTO buildingDTO = buildingMapper.toDto(building);
        return new ResponseEntity<BuildingDTO>(buildingDTO, HttpStatus.OK);
    }
    
    @GetMapping(value = "getManagerBuildings/{userId}", produces = "application/json")
    public ResponseEntity<?> getManagerBuildings(@PathVariable long userId){
    	List<Building> buildings = buildingService.findAllByManager(userId);
        List<BuildingDTO> buildingsDTO = buildingMapper.toDto(buildings);
        return new ResponseEntity<List<BuildingDTO>>(buildingsDTO, HttpStatus.OK);
    }
    
    @GetMapping(value = "getUserBuildings/{userId}", produces = "application/json")
    public ResponseEntity<?> getUserBuildings(@PathVariable long userId){
    	List<Building> buildings = buildingService.findAllByManager(userId);
        List<BuildingDTO> buildingsDTO = buildingMapper.toDto(buildings);
        return new ResponseEntity<List<BuildingDTO>>(buildingsDTO, HttpStatus.OK);
    }

	/**
	 * Handles BuildingExistsException that can happen when calling BuildingService.save(building)
	 */
	@ExceptionHandler(BuildingExistsException.class)
	public ResponseEntity<String> buildingExistsException(final BuildingExistsException e) {
		return new ResponseEntity<String>(
				"Building with address: " + e.getAddress() + ", city: " + e.getCity() + 
				", country: " + e.getCountry() + " already exists.", HttpStatus.CONFLICT);
	}
	
	/**
	 * Handles BuildingMissingException that can happen when calling BuildingService.findOne(buildingId)
	 */
	@ExceptionHandler(BuildingMissingException.class)
	public ResponseEntity<String> buildingMissingException(final BuildingMissingException e) {
		return new ResponseEntity<String>("Building with id: " + e.getId() + " doesnt exist.", HttpStatus.NOT_FOUND);
	}

}
