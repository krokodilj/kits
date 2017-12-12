package com.timsedam.buildingmanagement.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timsedam.buildingmanagement.dto.BuildingDTO;
import com.timsedam.buildingmanagement.dto.CreateBuildingDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.util.mappers.BuildingMapper;

@RestController
@RequestMapping(value = "api/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    private BuildingMapper buildingMapper=new BuildingMapper();

    @PostMapping(consumes = "application/json")
    public ResponseEntity create(
            @Valid @RequestBody CreateBuildingDTO createBuildingDTO, BindingResult validationResult){

        if (validationResult.hasErrors())
            return new ResponseEntity(validationResult.getAllErrors().toString(), HttpStatus.UNPROCESSABLE_ENTITY);

        Building building = buildingMapper.toModel(createBuildingDTO) ;
        building=buildingService.createBuilding(building);
        if(building==null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        BuildingDTO buildingDTO = buildingMapper.toDto(building);
        return new ResponseEntity(buildingDTO,HttpStatus.OK);
    }

    @GetMapping(value="/{id}",produces = "application/json")
    public ResponseEntity get(@PathVariable long id){

        Building building=buildingService.findOneById(id);
        if(building==null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        BuildingDTO buildingDTO = buildingMapper.toDto(building);

        return new ResponseEntity(buildingDTO,HttpStatus.OK);
    }

    @GetMapping(value="/all",produces = "application/json")
    public ResponseEntity get(){

        List<Building> buildings = buildingService.getAll();
        if(buildings==null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        List<BuildingDTO> buildingsDTO = new ArrayList<BuildingDTO>();
        for(Building b:buildings){
            buildingsDTO.add(buildingMapper.toDto(b));
        }
        return new ResponseEntity(buildingsDTO,HttpStatus.OK);
    }
}
