package com.timsedam.buildingmanagement.controller;

import com.timsedam.buildingmanagement.dto.CreateResidenceDTO;
import com.timsedam.buildingmanagement.dto.ResidenceDTO;
import com.timsedam.buildingmanagement.model.Building;
import com.timsedam.buildingmanagement.model.Residence;
import com.timsedam.buildingmanagement.service.BuildingService;
import com.timsedam.buildingmanagement.service.ResidenceService;
import com.timsedam.buildingmanagement.util.mappers.ResidenceMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/residences/")
public class ResidenceController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ResidenceService residenceService;

    @Autowired
    private BuildingService buildingService;

    private ResidenceMapper residenceMapper = new ResidenceMapper();

    /**
     * Create resident
     * @param createResidenceDTO
     * @return ResidentDTO
     */
    @PostMapping(consumes="application/json")
    public ResponseEntity create(
            @Valid @RequestBody CreateResidenceDTO createResidenceDTO,
            BindingResult validationResult)
    {
        if (validationResult.hasErrors())
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);

        //if building does not exists
        Building b = buildingService.findOneById(createResidenceDTO.getBuilding());
        if(b==null)
            return new ResponseEntity("Building not found",HttpStatus.NOT_FOUND);

        //if apartment number is taken
        boolean is_taken=false;
        if (b.getResidences()!=null)
            for(Residence r:b.getResidences())
                if(r.getApartmentNumber()==createResidenceDTO.getApartmentNumber())
                    is_taken=true;
        if(is_taken)
            return new ResponseEntity("Apartment number taken",HttpStatus.CONFLICT);

        Residence residence = residenceMapper.toModel(createResidenceDTO,b);
        residence=residenceService.createResidence(residence);
        if(residence==null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        ResidenceDTO residenceDTO = residenceMapper.toDto(residence);
        return new ResponseEntity(residenceDTO,HttpStatus.CREATED);
    }

    /**
     * Get residence by id
     * @param id
     * @return ResidenceDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity get(@PathVariable long id){
        Residence residence = residenceService.findOneById(id);
        if(residence==null)
            return new ResponseEntity("Residence not found",HttpStatus.NOT_FOUND);
        ResidenceDTO residenceDTO = residenceMapper.toDto(residence);
        return new ResponseEntity(residenceDTO,HttpStatus.OK);
    }


    /**
     * Get residence by building
     * @param buildingId
     * @return List<ResidenceDTO>
     */
    @GetMapping(value = "/by_building/{buildingId}")
    public ResponseEntity getByBuilding(@PathVariable long buildingId){
        List<ResidenceDTO> residenceDTOS = new ArrayList<ResidenceDTO>();
        Building building=buildingService.findOneById(buildingId);
        if(building==null)
            return new ResponseEntity("Building not found",HttpStatus.NOT_FOUND);
        List<Residence> residences = residenceService.getAllByBuilding(building);
        if(residences==null)
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        for(Residence r :residences){
            residenceDTOS.add(residenceMapper.toDto(r));
        }

        return new ResponseEntity(residenceDTOS,HttpStatus.OK);
    }


}