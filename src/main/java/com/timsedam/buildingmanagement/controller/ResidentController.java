package com.timsedam.buildingmanagement.controller;

import com.timsedam.buildingmanagement.dto.UserDTO;
import com.timsedam.buildingmanagement.dto.UserRegisterDTO;
import com.timsedam.buildingmanagement.model.*;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.ResidenceService;
import com.timsedam.buildingmanagement.service.RoleService;
import com.timsedam.buildingmanagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/residents/")
public class ResidentController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResidenceService residenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Register new resident
     * @param userRegisterDTO
     * @return resident id
     */
    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO,
            BindingResult validationResult
            )
    {
        //if dto has invalid input
        if (validationResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        //if username already in use
        if(userService.exists(userRegisterDTO.getUsername()))
            return new ResponseEntity<>("Username in use",HttpStatus.CONFLICT);

        Resident resident = (Resident) modelMapper.map(userRegisterDTO, Resident.class);
        resident = (Resident) userService.createUser(resident);

        if (resident == null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity(resident.getId(),HttpStatus.CREATED);
    }


    /**
     * Set new residence to its new resident
     * if resident has another residence delete it
     * @param residentId
     * @param residenceId
     * @return ResidentDTO
     */
    @PutMapping(value = "/{residentId}/add_to_residence/{residenceId}")
    public ResponseEntity addBuilding(
            @PathVariable long residentId,
            @PathVariable long residenceId
    ){
        //if resident exists
        Resident resident =(Resident) userService.findOne(residentId);
        if (resident == null)
            return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);

        //if residence exists
        Residence residence = residenceService.findOneById(residenceId);
        if (residence == null)
            return new ResponseEntity("Residence not found", HttpStatus.NOT_FOUND);


        residence.getResidents().add(resident);
        resident.getResidences().add(residence);
        userService.save(resident);

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Set residence to its new owner,
     * if residence has owner switch owners
     * @param residentId
     * @param residenceId
     * @return
     */
    @PutMapping(value = "/{residentId}/add_to_owner/{residenceId}")
    public ResponseEntity addToOwned(
            @PathVariable long residentId,
            @PathVariable long residenceId
    ){
        //if resident exists
        Resident resident =(Resident) userService.findOne(residentId);
        if (resident == null)
            return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);

        //if residence exists
        Residence residence = residenceService.findOneById(residenceId);
        if (residence == null)
            return new ResponseEntity("Residence not found", HttpStatus.NOT_FOUND);

        residence.setApartmentOwner(resident);
        resident.getOwnedApartments().add(residence);
        userService.save(resident);

        return new ResponseEntity(HttpStatus.OK);
    }
}
