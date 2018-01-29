package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import com.timsedam.buildingmanagement.dto.response.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.timsedam.buildingmanagement.dto.request.UserRegisterDTO;
import com.timsedam.buildingmanagement.exceptions.ResidenceExistsException;
import com.timsedam.buildingmanagement.exceptions.ResidenceMissingException;
import com.timsedam.buildingmanagement.exceptions.RoleInvalidException;
import com.timsedam.buildingmanagement.exceptions.UserExistsException;
import com.timsedam.buildingmanagement.exceptions.UserMissingException;
import com.timsedam.buildingmanagement.mapper.UserMapper;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.model.User;
import com.timsedam.buildingmanagement.service.ResidenceService;
import com.timsedam.buildingmanagement.service.ResidentService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/residents/")
public class 	ResidentController {
	
	@Autowired
	private ResidentService residentService;
	
	@Autowired
	private ResidenceService residenceService;	
	
    @Autowired
    private UserMapper userMapper;

    @GetMapping(value="/by_building/{buildingId}" , produces="application/json")
	public ResponseEntity<List<UserDTO>> getByBuilding(@PathVariable long buildingId){

    	List<User> residents = residentService.findAllByBuildingId(buildingId);
    	List<UserDTO> userDTOS = new ArrayList<UserDTO>();
    	for(User u :residents){
    		userDTOS.add(userMapper.toDto(u));
		}
    	return new ResponseEntity<List<UserDTO>>(userDTOS,HttpStatus.OK);
	}

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult validationResult) 
    		throws UserExistsException, RoleInvalidException
    {
        if (validationResult.hasErrors()) {
        	String errorMessage = validationResult.getFieldError().getDefaultMessage();
            return new ResponseEntity<String>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        else {
            User resident = userMapper.toModel(userRegisterDTO);
            resident = residentService.save(resident);

            return new ResponseEntity<>(resident.getId(), HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/{residentId}/add_to_residence/{residenceId}")
    public ResponseEntity<?> addBuilding(@PathVariable long residentId, @PathVariable long residenceId) 
    		throws UserExistsException, RoleInvalidException, UserMissingException, ResidenceMissingException {
    	residenceService.addResident(residenceId, residentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{residentId}/add_to_owner/{residenceId}")
    public ResponseEntity<?> addToOwner(@PathVariable long residentId, @PathVariable long residenceId) 
    		throws UserExistsException, RoleInvalidException, UserMissingException, ResidenceMissingException, ResidenceExistsException {

    	User resident = residentService.findOne(residentId);
        Residence residence = residenceService.findOneById(residenceId);

        residence.setApartmentOwner(resident);        
        residenceService.save(residence);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserDTO>> getAll(){

        List<User> residents = residentService.getAll();
        List<UserDTO> userDTOS = new ArrayList<UserDTO>();
        for(User u : residents)
            userDTOS.add(userMapper.toDto(u));

        return new ResponseEntity<List<UserDTO>>(userDTOS,HttpStatus.OK);
    }
    
    /**
	 * Handles UserMissingException that can happen when calling:
	 * - ResidenceService.addResident(residenceId, userId)
	 * - ResidentService.findOne(userId)
	 */
	@ExceptionHandler(UserMissingException.class)
	public ResponseEntity<String> userMissingException(final UserMissingException e) {
		return new ResponseEntity<String>("User with ID: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
    /**
	 * Handles ResidenceMissingException that can happen when calling:
	 * - ResidenceService.addResident(residenceId, userId)
	 * - ResidenceService.findOne(residenceId)
	 */
	@ExceptionHandler(ResidenceMissingException.class)
	public ResponseEntity<String> residenceMissingException(final ResidenceMissingException e) {
		return new ResponseEntity<String>("Residence with ID: " + e.getId() + " doesn't exist.", HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Handles UserExistsException that can happen when calling UserService.save(user)
	 */
	@ExceptionHandler(UserExistsException.class)
	public ResponseEntity<String> userExistsException(final UserExistsException e) {
		return new ResponseEntity<String>("Resident with username: " + e.getUsername() + " already exists.", HttpStatus.NOT_FOUND);
	}

}
