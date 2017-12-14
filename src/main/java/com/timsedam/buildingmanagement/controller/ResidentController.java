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
@RequestMapping(value = "api/residents")
public class ResidentController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResidenceService residenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity create(
            @Valid @RequestBody UserRegisterDTO userRegisterDTO,
            BindingResult validationResult
            )
    {
        if (validationResult.hasErrors())
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);

        if(userService.exists(userRegisterDTO.getUsername()))
            return new ResponseEntity<>("Username in use",HttpStatus.BAD_REQUEST);

        Resident resident = (Resident) modelMapper.map(userRegisterDTO, Resident.class);

        Role role = roleService.findOneByName("USER");
        resident.setRole(role);
        userService.save(resident);

        UserDTO residentDTO = modelMapper.map(resident, UserDTO.class);
        return new ResponseEntity(residentDTO,HttpStatus.OK);
    }

    @PutMapping(value = "/{residentId}/add_to_residence/{residenceId}")
    public ResponseEntity addBuilding(
            @PathVariable long residentId,
            @PathVariable long residenceId
    ){
        Resident resident =(Resident) userService.findOne(residentId);
        if (resident == null)
            return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);

        Residence residence = residenceService.findOneById(residenceId);
        if (residence == null)
            return new ResponseEntity("Residence not found", HttpStatus.NOT_FOUND);

        residence.getResidents().add(resident);
        resident.getResidences().add(residence);
        userService.save(resident);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping(value = "/{residentId}/add_to_owned_residence/{residenceId}")
    public ResponseEntity addToOwned(
            @PathVariable long residentId,
            @PathVariable long residenceId
    ){
        Resident resident =(Resident) userService.findOne(residentId);
        if (resident == null)
            return new ResponseEntity("User not found",HttpStatus.NOT_FOUND);

        Residence residence = residenceService.findOneById(residenceId);
        if (residence == null)
            return new ResponseEntity("Residence not found", HttpStatus.NOT_FOUND);

        residence.setApartmentOwner(resident);
        resident.getOwnedApartments().add(residence);
        userService.save(resident);

        return new ResponseEntity(HttpStatus.OK);
    }
}
