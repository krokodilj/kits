package com.timsedam.buildingmanagement.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.CreateBuildingDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.service.BuildingService;

@RestController
@RequestMapping(value = "api/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(consumes = "application/json")
    public ResponseEntity create(
            @Valid @RequestBody CreateBuildingDTO createBuildingDTO, BindingResult validationResult){

        if (validationResult.hasErrors()) {
            return new ResponseEntity(validationResult.getAllErrors().toString(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Building building = modelMapper.map(createBuildingDTO,Building.class);
        building=buildingService.createBuilding(building);

        if(building==null){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(building,HttpStatus.OK);
    }
}
