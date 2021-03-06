package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


@RestController
@RequestMapping(value = "/api/residents/")
public class ResidentController {
	
	@Autowired
	private ResidentService residentService;
	
	@Autowired
	private ResidenceService residenceService;	
	
    @Autowired
    private UserMapper userMapper;

    /**
     * Register new resident
     * @param userRegisterDTO
     * @return resident id
     */
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

    /**
     * Set new residence to its new resident
     * if resident has another residence delete it
     * @param residentId
     * @param residenceId
     * @return
     * @throws UserExistsException
     * @throws RoleInvalidException
     * @throws UserMissingException
     * @throws ResidenceMissingException
     */
    @PutMapping(value = "/{residentId}/add_to_residence/{residenceId}")
    public ResponseEntity<?> addBuilding(@PathVariable long residentId, @PathVariable long residenceId) 
    		throws UserExistsException, RoleInvalidException, UserMissingException, ResidenceMissingException {
    	residenceService.addResident(residenceId, residentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Set residence to its new owner,
     * if residence has owner switch owners
     * @param residentId
     * @param residenceId
     * @return
     * @throws UserExistsException 
     * @throws RoleInvalidException 
     * @throws UserMissingException 
     * @throws ResidenceMissingException 
     * @throws ResidenceExistsException 
     */
    @PutMapping(value = "/{residentId}/add_to_owner/{residenceId}")
    public ResponseEntity<?> addToOwner(@PathVariable long residentId, @PathVariable long residenceId) 
    		throws UserExistsException, RoleInvalidException, UserMissingException, ResidenceMissingException, ResidenceExistsException {

    	User resident = residentService.findOne(residentId);
        Residence residence = residenceService.findOneById(residenceId);

        residence.setApartmentOwner(resident);        
        residenceService.save(residence);
        return new ResponseEntity<>(HttpStatus.OK);
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
